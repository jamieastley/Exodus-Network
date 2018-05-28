package com.jastley.warmindfordestiny2.Characters.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import android.widget.Toast;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Characters.adapters.CharacterItemsRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.holders.TransferItemViewHolder;
import com.jastley.warmindfordestiny2.Characters.interfaces.TransferSelectListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Utils.UnsignedHashConverter;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.InventoryItemDAO;
import com.jastley.warmindfordestiny2.database.models.DestinyInventoryItemDefinition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterInventoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharacterInventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterInventoryFragment extends Fragment implements TransferSelectListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private static final int ARG_TAB_NUMBER;

    // TODO: Rename and change types of parameters
    private int mTabNumber;
    private CharacterDatabaseModel mCharacter;

    @BindView(R.id.inventory_items_recyclerview) RecyclerView mItemsRecyclerView;
    @BindView(R.id.inventory_items_progress) ProgressBar loadingProgress;
    @BindView(R.id.inventory_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    private CharacterItemsRecyclerAdapter mItemsRecyclerAdapter;

    private OnFragmentInteractionListener mListener;
    private BungieAPI mBungieAPI;
    private List<InventoryItemModel> itemList = new ArrayList<>();
    private List<CharacterDatabaseModel> mCharacterList = new ArrayList<>();
//    private List<DestinyInventoryItemDefinition> mCollectablesList = new ArrayList<>();
    ItemTransferDialogFragment transferModalDialog;

    boolean mIsRestoredFromBackstack;

    public CharacterInventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment CharacterInventoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharacterInventoryFragment newInstance(int tabNumber,
                                                         CharacterDatabaseModel character,
                                                         ArrayList<CharacterDatabaseModel> characterList,
                                                         ArrayList<DestinyInventoryItemDefinition> destinyInventoryItemDefinitionManifest) {
        CharacterInventoryFragment fragment = new CharacterInventoryFragment();
        Bundle args = new Bundle();
        System.out.println("Fragment created, tabIndex: " + tabNumber);
        args.putInt("ARG_TAB_NUMBER", tabNumber);
        args.putParcelable("ARG_CHARACTER_DATA", character);
        args.putParcelableArrayList("ARG_CHARACTER_LIST", characterList);
        args.putParcelableArrayList("ARG_COLLECTABLES_MANIFEST", destinyInventoryItemDefinitionManifest);
        fragment.setArguments(args);
//        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabNumber = getArguments().getInt("ARG_TAB_NUMBER");
            mCharacter = getArguments().getParcelable("ARG_CHARACTER_DATA");
            mCharacterList = getArguments().getParcelableArrayList("ARG_CHARACTER_LIST");
            System.out.println("onCreate tab "+mTabNumber+", "+mCharacter.getClassType());
//            mCollectablesList = getArguments().getParcelableArrayList("ARG_COLLECTABLES_MANIFEST");
        }

        mIsRestoredFromBackstack = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_character_inventory, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        mBungieAPI = RetrofitHelper.getAuthBungieAPI(getContext(), baseURL);

//        if (savedInstanceState != null) {
//            itemList = savedInstanceState.getParcelableArrayList("characterItems");
//            setRecyclerView(itemList);
//        }
//        else {

        String classType = mCharacter.getClassType();

        if(classType.equals("vault")){
            //get Vault items only
            getVaultInventory(
                    mCharacter.getMembershipType(),
                    mCharacter.getMembershipId());
        }
        else {
            getCharacterInventory(
                mCharacter.getMembershipType(),
                mCharacter.getMembershipId(),
                mCharacter.getCharacterId());
        }

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(true);

            if(classType.equals("vault")){
                //get Vault items only
                getVaultInventory(
                        mCharacter.getMembershipType(),
                        mCharacter.getMembershipId());
            }
            else {
                getCharacterInventory(
                        mCharacter.getMembershipType(),
                        mCharacter.getMembershipId(),
                        mCharacter.getCharacterId());
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            System.out.println("FragmentInteraction triggered");
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mIsRestoredFromBackstack){

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsRestoredFromBackstack = true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("characterItems", (ArrayList<? extends Parcelable>) itemList);
    }

    @Override
    public void onTransferSelect(View view, int position, TransferItemViewHolder holder) {
        Toast.makeText(getContext(), " selected!!!?", Toast.LENGTH_SHORT).show();
        System.out.println("onTransferSelect triggered");
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }

    public void getCharacterInventory(String membershipType, String membershipId, String characterId) {

        mBungieAPI.getCharacterInventory(membershipType, membershipId, characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Error code checking
                    switch(response.getErrorCode()){
                        case 1: //Response successful

                            List<String> itemHashList = new ArrayList<>();
                            List<String> unsignedHashList = new ArrayList<>();

                            itemList.clear();
                            for(int i = 0; i < response.getResponse().getInventory().getData().getItems().size(); i++){

                                String primaryStatValue;
                                String itemInstanceId = null;

                                //add/calculate hashes
                                String itemHash = String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash());
                                String unsignedHash = UnsignedHashConverter.convert(Long.valueOf(itemHash));
                                itemHashList.add(itemHash);
                                unsignedHashList.add(unsignedHash);


                                InventoryItemModel itemModel = new InventoryItemModel();

                                itemModel.setItemHash(itemHash);

                                //Get instanceId to look up it's instanceData from the response
                                if(response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId() != null) {
                                    itemInstanceId = response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId();
                                    itemModel.setItemInstanceId(itemInstanceId);

                                    //get damageType (if != null)
                                    if(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getDamageType() != null){
                                        itemModel.setDamageType(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getDamageType());
                                    }

                                }

                                try{
                                    primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
                                    itemModel.setPrimaryStatValue(primaryStatValue);


                                }
                                catch(Exception e){
                                    System.out.println("Not an instance-specific item");
                                }
//
                                itemModel.setBucketHash(response.getResponse().getInventory().getData().getItems().get(i).getBucketHash());
                                //Required to manipulate UI on transfer/equip modal
                                itemModel.setClassType(mCharacter.getClassType());
                                itemModel.setTabIndex(mTabNumber);
                                itemList.add(itemModel);
                            }

                            getManifestData(itemHashList, unsignedHashList);
//                            setRecyclerView(itemList);
                            break;

                        case 5: //maintainence
                            Snackbar.make(getParentFragment().getView().findViewById(R.id.activity_inventory_main_content), "Bungie servers are currently unavailable.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                            break;
                    }


                }, Throwable -> {
                    Snackbar.make(getParentFragment().getView().findViewById(R.id.activity_inventory_main_content), "Couldn't retrieve account data.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
//                        getActivity().onBackPressed();
                    });

    }

    public void getVaultInventory(String membershipType, String membershipId) {

        mBungieAPI.getVaultInventory(membershipType, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Error code checking
                    switch(response.getErrorCode()){
                        case 1: //Response successful

                            List<String> itemHashList = new ArrayList<>();
                            List<String> unsignedHashList = new ArrayList<>();

                            itemList.clear();
                            /** FOR VAULT INVENTORY, USE getProfileInventory(), NOT getInventory() **/
                            for(int i = 0; i < response.getResponse().getProfileInventory().getData().getItems().size(); i++){

                                String primaryStatValue;
                                String itemInstanceId = null;

                                //add/calculate hashes
                                String itemHash = String.valueOf(response.getResponse().getProfileInventory().getData().getItems().get(i).getItemHash());
                                String unsignedHash = UnsignedHashConverter.convert(Long.valueOf(itemHash));
                                itemHashList.add(itemHash);
                                unsignedHashList.add(unsignedHash);


                                InventoryItemModel itemModel = new InventoryItemModel();

                                itemModel.setItemHash(itemHash);

                                //Get instanceId to look up it's instanceData from the response
                                if(response.getResponse().getProfileInventory().getData().getItems().get(i).getItemInstanceId() != null) {
                                    itemInstanceId = response.getResponse().getProfileInventory().getData().getItems().get(i).getItemInstanceId();
                                    itemModel.setItemInstanceId(itemInstanceId);

                                    //get damageType (if != null)
                                    if(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getDamageType() != null){
                                        itemModel.setDamageType(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getDamageType());
                                    }

                                }

                                try{
                                    primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
                                    itemModel.setPrimaryStatValue(primaryStatValue);


                                }
                                catch(Exception e){
                                    System.out.println("Not an instance-specific item");
                                }
//
                                itemModel.setBucketHash(response.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash());
                                //Required to manipulate UI on transfer/equip modal
                                itemModel.setClassType(mCharacter.getClassType());
                                itemModel.setTabIndex(mTabNumber);
                                itemList.add(itemModel);
                            }

                            getManifestData(itemHashList, unsignedHashList);
                            break;

                        case 5: //maintainence
                            Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "Bungie servers are currently unavailable.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                            break;

                        case 7: //ParameterParseFailure
                            Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "ParameterParseFailure: Developer broke something here, sorry :(.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                            break;

                        case 8: //ParameterInvalidRange
                            Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "ParameterInvalidRange: Developer broke something here, sorry :(", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                            break;
                    }


                }, Throwable -> {
                    Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "Couldn't retrieve/parse account data.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
//                        getActivity().onBackPressed();
                });

    }


    public void getManifestData(List<String> itemList, List<String> unsigned){

        InventoryItemDAO mInventoryItemDAO = AppDatabase.getAppDatabase(getContext()).getInventoryItemDAO();

        mInventoryItemDAO.getItemsListByKey(itemList, unsigned)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {

                    for (int i = 0; i < items.size(); i++) {

                        JsonParser parser = new JsonParser();
                        JsonObject itemObj = (JsonObject) parser.parse(items.get(i).getValue());

                        /* Room returns manifest values in alphanumeric key order, so use
                        inner loop to ensure that the item in each list is the same within each list we are iterating through */
                        for (int j = 0; j < this.itemList.size(); j++) {

                            if(this.itemList.get(j).getItemHash().equals(itemObj.get("hash").getAsString())){

                                this.itemList.get(j).setItemName(itemObj.get("displayProperties").getAsJsonObject().get("name").getAsString());
                                try{
                                    Log.d("InventoryAPIListHash: ", this.itemList.get(j).getItemHash());
                                    Log.d("ManifestItemHash: ", itemObj.get("hash").getAsString());
                                    this.itemList.get(j).setItemIcon(itemObj.get("displayProperties").getAsJsonObject().get("icon").getAsString());
                                }
                                catch(Exception e){
                                    Log.d("getManifestData: ", e.getLocalizedMessage());
                                }
                            }
                        }

                    }
                    setRecyclerView(this.itemList);
                });
    }

    public void setRecyclerView(List<InventoryItemModel> itemList){

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());

        mItemsRecyclerAdapter = new CharacterItemsRecyclerAdapter(getContext(), itemList, (view, position, holder) -> {
            Toast.makeText(getContext(), holder.getItemName().getText().toString() + " clicked", Toast.LENGTH_SHORT).show();

            //get properties of clicked item
            InventoryItemModel clickedItem = new InventoryItemModel();
            clickedItem.setBucketHash(holder.getBucketHash());
            clickedItem.setItemInstanceId(holder.getItemInstanceId());
            clickedItem.setIsEquipped(holder.getIsEquipped());
            clickedItem.setCanEquip(holder.getCanEquip());
            clickedItem.setItemHash(holder.getItemHash());
            clickedItem.setItemName(holder.getItemName().getText().toString());
            clickedItem.setItemIcon(holder.getImageUrl());
            clickedItem.setPrimaryStatValue(holder.getPrimaryStatValue());
            clickedItem.setItemTypeDisplayName(holder.getItemTypeDisplayName());
            clickedItem.setDamageType(holder.getDamageType());

            //for transferring TO vault
            clickedItem.setTabIndex(holder.getTabIndex());
            clickedItem.setVaultCharacterId(mCharacter.getCharacterId());

            transferModalDialog = new ItemTransferDialogFragment();
            Bundle args = new Bundle();
            args.putParcelable("selectedItem", clickedItem);
            args.putParcelableArrayList("characterList", (ArrayList<? extends Parcelable>) mCharacterList); //TODO HERERERERE
            args.putInt("tabIndex", mTabNumber);
            transferModalDialog.setArguments(args);

            transferModalDialog.show(getChildFragmentManager(), "transferModalDialog");


//            transferModalDialog.onDismiss(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//
//                }
//            });
//            transferModalDialog.onDismiss(dialog -> {
//
//
//            });
        });
        mSwipeRefreshLayout.setRefreshing(false);

        mItemsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mItemsRecyclerView.setAdapter(mItemsRecyclerAdapter);
        mItemsRecyclerAdapter.notifyDataSetChanged();
        loadingProgress.setVisibility(View.GONE);
    }

    public void refreshInventory() {
        String classType = mCharacter.getClassType();

        if(classType.equals("vault")){
            //get Vault items only
            getVaultInventory(
                    mCharacter.getMembershipType(),
                    mCharacter.getMembershipId());
        }
        else {
            getCharacterInventory(
                    mCharacter.getMembershipType(),
                    mCharacter.getMembershipId(),
                    mCharacter.getCharacterId());
        }
    }

}

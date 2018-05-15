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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import android.widget.Toast;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Definitions;
import com.jastley.warmindfordestiny2.Characters.adapters.CharacterItemsRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.holders.TransferItemViewHolder;
import com.jastley.warmindfordestiny2.Characters.interfaces.TransferSelectListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.DatabaseHelper;
import com.jastley.warmindfordestiny2.database.OldDatabaseModel;
import com.jastley.warmindfordestiny2.database.models.Collectables;

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
    private List<InventoryItemModel> inventoryItems = new ArrayList<>();
    private List<CharacterDatabaseModel> mCharacterList = new ArrayList<>();
//    private List<Collectables> mCollectablesList = new ArrayList<>();
    private DatabaseHelper db;
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
                                                         ArrayList<Collectables> collectablesManifest) {
        CharacterInventoryFragment fragment = new CharacterInventoryFragment();
        Bundle args = new Bundle();
        System.out.println("Fragment created, tabIndex: " + tabNumber);
        args.putInt("ARG_TAB_NUMBER", tabNumber);
        args.putParcelable("ARG_CHARACTER_DATA", character);
        args.putParcelableArrayList("ARG_CHARACTER_LIST", characterList);
        args.putParcelableArrayList("ARG_COLLECTABLES_MANIFEST", collectablesManifest);
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

        db = new DatabaseHelper(getContext());
//        mBungieAPI = new RetrofitHelper().getAuthBungieAPI(getContext());
//
//
//        getCharacterInventory(
//                mCharacter.getMembershipType(),
//                mCharacter.getMembershipId(),
//                mCharacter.getCharacterId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_character_inventory, container, false);

        ButterKnife.bind(this, rootView);

//        if (savedInstanceState != null) {
//            inventoryItems = savedInstanceState.getParcelableArrayList("characterItems");
//            setRecyclerView(inventoryItems);
//        }
//        else {
//            getCharacterInventory(
//                    mCharacter.getMembershipType(),
//                    mCharacter.getMembershipId(),
//                    mCharacter.getCharacterId());
//        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        mBungieAPI = new RetrofitHelper().getAuthBungieAPI(getContext(), baseURL);

//        if (savedInstanceState != null) {
//            inventoryItems = savedInstanceState.getParcelableArrayList("characterItems");
//            setRecyclerView(inventoryItems);
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

        outState.putParcelableArrayList("characterItems", (ArrayList<? extends Parcelable>) inventoryItems);
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

        JsonParser parser = new JsonParser();

        mBungieAPI.getCharacterInventory(membershipType, membershipId, characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Error code checking
                    switch(response.getErrorCode()){
                        case 1: //Response successful

                            inventoryItems.clear();
                            for(int i = 0; i < response.getResponse().getInventory().getData().getItems().size(); i++){

                                String primaryStatValue;
                                String itemInstanceId = null;
                                String itemHash = String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash());
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
                                //Lookup manifest data for item
                                try {
//                                JsonArray json =  mCollectablesList;
//                                String name = json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString();
//                                itemModel.setItemName(json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString());
//                                itemModel.setItemIcon(json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("icon").getAsString());
//                                //                                itemModel.setItemName(mCollectablesList.indexOf(1));
                                    OldDatabaseModel dbItem = db.getCollectablesData("Collectables", itemHash);
//                                String dataString = dbItem.getValue();
                                    JsonObject itemData = (JsonObject) parser.parse(dbItem.getValue());
                                        itemModel.setItemName(itemData.get("displayProperties").getAsJsonObject().get("name").getAsString());
                                    itemModel.setItemIcon(itemData.get("displayProperties").getAsJsonObject().get("icon").getAsString());
                                    itemModel.setItemTypeDisplayName(itemData.get("itemTypeDisplayName").getAsString());
                                    itemModel.setIsEquipped(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getIsEquipped());
                                    itemModel.setCanEquip(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getCanEquip());



                                }
                                catch(Exception e){
                                    System.out.println(e);
                                }
                                db.close();
//                            CollectablesDAO mCollectablesDAO = AppDatabase.getAppDatabase(getContext()).getCollectablesDAO();
//                            mCollectablesDAO.getItemByKey(itemHash)
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(collectables -> {
////                                       collectables.
//                                        JsonParser parser = new JsonParser();
//                                        JsonElement itemData = parser.parse(collectables.getValue());
//                                        itemModel.setItemName(itemData.getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString());
//                                        itemModel.setItemIcon(itemData.getAsJsonObject().get("displayProperties").getAsJsonObject().get("icon").getAsString());
//                                    });


//                            canEquip = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getCanEquip();
                                try{
                                    primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
                                    itemModel.setPrimaryStatValue(primaryStatValue);


                                }
                                catch(Exception e){
                                    System.out.println("Not an instance-specific item");
                                }
//
                                //get inventory item
//                            itemModel.setItemHash(String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash()));
                                itemModel.setBucketHash(response.getResponse().getInventory().getData().getItems().get(i).getBucketHash());
                                //Required to manipulate UI on transfer/equip modal
                                itemModel.setClassType(mCharacter.getClassType());
                                itemModel.setTabIndex(mTabNumber);
                                inventoryItems.add(itemModel);
                            }

                            setRecyclerView(inventoryItems);
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

        JsonParser parser = new JsonParser();

        mBungieAPI.getVaultInventory(membershipType, membershipId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    //Error code checking
                    switch(response.getErrorCode()){
                        case 1: //Response successful

                            inventoryItems.clear();
                            for(int i = 0; i < response.getResponse().getProfileInventory().getData().getItems().size(); i++){

                                String primaryStatValue;
                                String itemInstanceId = null;
                                String itemHash = String.valueOf(response.getResponse().getProfileInventory().getData().getItems().get(i).getItemHash());
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
                                //Lookup manifest data for item
                                try {
//                                JsonArray json =  mCollectablesList;
//                                String name = json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString();
//                                itemModel.setItemName(json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString());
//                                itemModel.setItemIcon(json.getAsJsonObject().get(itemHash).getAsJsonObject().get("displayProperties").getAsJsonObject().get("icon").getAsString());
//                                //                                itemModel.setItemName(mCollectablesList.indexOf(1));
                                    OldDatabaseModel dbItem = db.getCollectablesData("Collectables", itemHash);
//                                String dataString = dbItem.getValue();
                                    JsonObject itemData = (JsonObject) parser.parse(dbItem.getValue());
                                    itemModel.setItemName(itemData.get("displayProperties").getAsJsonObject().get("name").getAsString());
                                    itemModel.setItemIcon(itemData.get("displayProperties").getAsJsonObject().get("icon").getAsString());
                                    itemModel.setItemTypeDisplayName(itemData.get("itemTypeDisplayName").getAsString());
                                    itemModel.setIsEquipped(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getIsEquipped());
                                    itemModel.setCanEquip(response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getCanEquip());


                                }
                                catch(Exception e){
                                    System.out.println(e);
                                }
                                db.close();
//                            CollectablesDAO mCollectablesDAO = AppDatabase.getAppDatabase(getContext()).getCollectablesDAO();
//                            mCollectablesDAO.getItemByKey(itemHash)
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(collectables -> {
////                                       collectables.
//                                        JsonParser parser = new JsonParser();
//                                        JsonElement itemData = parser.parse(collectables.getValue());
//                                        itemModel.setItemName(itemData.getAsJsonObject().get("displayProperties").getAsJsonObject().get("name").getAsString());
//                                        itemModel.setItemIcon(itemData.getAsJsonObject().get("displayProperties").getAsJsonObject().get("icon").getAsString());
//                                    });


//                            canEquip = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getCanEquip();
                                try{
                                    primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
                                    itemModel.setPrimaryStatValue(primaryStatValue);


                                }
                                catch(Exception e){
                                    System.out.println("Not an instance-specific item");
                                }
//
                                //get inventory item
//                            itemModel.setItemHash(String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash()));
                                String bucketHash = response.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash();
                                if(bucketHash.equals(Definitions.vault)) {
                                    itemModel.setBucketHash(response.getResponse().getProfileInventory().getData().getItems().get(i).getBucketHash());
                                    //Required to manipulate UI on transfer/equip modal
                                    itemModel.setClassType(mCharacter.getClassType());
                                    itemModel.setTabIndex(mTabNumber);
                                    inventoryItems.add(itemModel);
                                }
                            }

                            setRecyclerView(inventoryItems);
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

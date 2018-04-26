package com.jastley.warmindfordestiny2.Characters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Characters.adapters.CharacterItemsRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.CollectablesDAO;
import com.jastley.warmindfordestiny2.database.DatabaseHelper;
import com.jastley.warmindfordestiny2.database.OldDatabaseModel;
import com.jastley.warmindfordestiny2.database.models.Collectables;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterInventoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharacterInventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterInventoryFragment extends Fragment {
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
    private CharacterItemsRecyclerAdapter mItemsRecyclerAdapter;

    private OnFragmentInteractionListener mListener;
    private BungieAPI mBungieAPI;
    private List<InventoryItemModel> inventoryItems = new ArrayList<>();
//    private List<Collectables> mCollectablesList = new ArrayList<>();
    private DatabaseHelper db;

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
    public static CharacterInventoryFragment newInstance(int tabNumber, CharacterDatabaseModel character, ArrayList<Collectables> collectablesManifest) {
        CharacterInventoryFragment fragment = new CharacterInventoryFragment();
        Bundle args = new Bundle();
        args.putInt("ARG_TAB_NUMBER", tabNumber);
        args.putParcelable("ARG_CHARACTER_DATA", character);
        args.putParcelableArrayList("ARG_COLLECTABLES_MANIFEST", collectablesManifest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabNumber = getArguments().getInt("ARG_TAB_NUMBER");
            mCharacter = getArguments().getParcelable("ARG_CHARACTER_DATA");
//            mCollectablesList = getArguments().getParcelableArrayList("ARG_COLLECTABLES_MANIFEST");
        }

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

        mBungieAPI = new RetrofitHelper().getAuthBungieAPI(getContext());

//        if (savedInstanceState != null) {
//            inventoryItems = savedInstanceState.getParcelableArrayList("characterItems");
//            setRecyclerView(inventoryItems);
//        }
//        else {
            getCharacterInventory(
                    mCharacter.getMembershipType(),
                    mCharacter.getMembershipId(),
                    mCharacter.getCharacterId());
//        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("characterItems", (ArrayList<? extends Parcelable>) inventoryItems);
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
        void onFragmentInteraction(Uri uri);
    }

    public void getCharacterInventory(String membershipType, String membershipId, String characterId) {

        JsonParser parser = new JsonParser();

        mBungieAPI.getCharacterInventory(membershipType, membershipId, characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
//                    response.getResponse().getInventory().getData().getItems().
                    for(int i = 0; i < response.getResponse().getInventory().getData().getItems().size(); i++){

                        String primaryStatValue;
                        String itemInstanceId = null;
                        String itemHash = String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash());
                        Boolean canEquip = false;
                        InventoryItemModel itemModel = new InventoryItemModel();

                        //Get instanceId to look up it's instanceData from the response
                        if(response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId() != null) {
                            itemInstanceId = response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId();
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


//                            canEquip = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).isCanEquip();
                        try{
                            primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
                            itemModel.setPrimaryStatValue(primaryStatValue);
                        }
                        catch(Exception e){
                            System.out.println("Not an instance-specific item");
                        }
                        itemModel.setItemInstanceId(response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId());

//                        }


                        //Cast to JsonObject as we don't have a model class for instance data (because dynamic key values)
//                        JsonObject obj = new JsonParser().parse(response.getResponse().getItemComponents().getInstances().getInstanceData().getAsJsonObject());
//
//                        //If we can equip the item (true), get the primaryStat value (damage/defense)
//                        if(obj.get(itemInstanceId).getAsJsonObject().get("canEquip").getAsBoolean()){
//                            primaryStatValue = obj.get(itemInstanceId).getAsJsonObject().get("primaryStat").getAsJsonObject().get("value").getAsDouble();
//                            itemModel.setPrimaryStatValue(primaryStatValue);
//                        }
//                        if(canEquip){
//                            primaryStatValue = response.getResponse().getItemComponents().getInstances().getInstanceData().get(itemInstanceId).getPrimaryStat().getValue();
//                            itemModel.setPrimaryStatValue(primaryStatValue);
//                            itemModel.setItemInstanceId(response.getResponse().getInventory().getData().getItems().get(i).getItemInstanceId());
//                        }

                            //get inventory item
//                            itemModel.setItemHash(String.valueOf(response.getResponse().getInventory().getData().getItems().get(i).getItemHash()));
                            itemModel.setBucketHash(response.getResponse().getInventory().getData().getItems().get(i).getBucketHash());
                            inventoryItems.add(itemModel);
                    }

                    setRecyclerView(inventoryItems);
//                    //create Recyclerview
//                    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
//                    mItemsRecyclerAdapter = new CharacterItemsRecyclerAdapter(getContext(), inventoryItems);
//                    mItemsRecyclerView.setLayoutManager(mLinearLayoutManager);
//                    mItemsRecyclerView.setAdapter(mItemsRecyclerAdapter);
//                    loadingProgress.setVisibility(View.GONE);
                });

    }

    public void setRecyclerView(List<InventoryItemModel> itemList){

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mItemsRecyclerAdapter = new CharacterItemsRecyclerAdapter(getContext(), itemList);
        mItemsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mItemsRecyclerView.setAdapter(mItemsRecyclerAdapter);
        loadingProgress.setVisibility(View.GONE);
    }
}

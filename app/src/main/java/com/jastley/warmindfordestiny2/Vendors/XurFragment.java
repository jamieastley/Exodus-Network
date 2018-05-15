package com.jastley.warmindfordestiny2.Vendors;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.Vendors.adapters.XurItemsRecyclerAdapter;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.Response_GetXurWeekly;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.database.AppDatabase;
import com.jastley.warmindfordestiny2.database.CollectablesDAO;
import com.jastley.warmindfordestiny2.database.FactionsDAO;
import com.squareup.picasso.Picasso;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static com.jastley.warmindfordestiny2.Definitions.theNine;
import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XurFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XurFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XurFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String xurURL = "https://whatsxurgot.com";

    private OnFragmentInteractionListener mListener;

    private String locationIndex;

    //Xur related stuff
    List<InventoryItemModel> xurItemsList = new ArrayList<>();
    @BindView(R.id.xur_region_text) TextView xurRegionText;
    @BindView(R.id.xur_world_text) TextView xurWorldText;
    @BindView(R.id.xur_location_banner) ImageView xurImageBanner;
    @BindView(R.id.xur_items_recycler_view) RecyclerView mXurRecyclerView;
    @BindView(R.id.xur_progress_bar) ProgressBar progressBar;

    XurItemsRecyclerAdapter mXurRecyclerAdapter;


//    final CollectablesDAO mCollectibleDAO = AppDatabase.getAppDatabase(context).getCollectablesDAO();
    public XurFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XurFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XurFragment newInstance(String param1, String param2) {
        XurFragment fragment = new XurFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_xur, container, false);

        ButterKnife.bind(this, rootView);

        // Inflate the layout for this fragment
        return rootView;


    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.xurInventory));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        getXurInventory();
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

    public void getXurInventory() {

        //get Xur inventory from WhatsXurGot API (throw error if Xur not available)
        BungieAPI mBungieAPI = new RetrofitHelper().getBungieAPI(xurURL);


        mBungieAPI.getXurWeeklyInventory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response_getXurWeekly -> {

                            if(!response_getXurWeekly.getResponse().getStatus().equals("200")) {
                                Snackbar.make(getView(), "Couldn't get Xur's stock from the server.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null)
                                        .show();
                                progressBar.setVisibility(View.GONE);
                            }
                            else {

                                System.out.println(response_getXurWeekly.toString());

                                xurWorldText.setText(response_getXurWeekly.getResponse().getData().getLocation().getWorld());

                                String region = response_getXurWeekly.getResponse().getData().getLocation().getRegion();
                                if(region.contains("&rsquo;")){
                                    region = region.replace("&rsquo;", "'");
                                }
                                xurRegionText.setText(region);

                                locationIndex = response_getXurWeekly.getResponse().getData().getLocation().getId();

                                int listSize = response_getXurWeekly.getResponse().getData().getItems().size();


                                //add all items to List<>
                                for(int i = 0; i < listSize; i++){

                                    InventoryItemModel xurItem = new InventoryItemModel();

                                    xurItem.setItemName(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getName());
                                    xurItem.setItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getDisplayProperties().getIcon());
                                    xurItem.setItemHash(response_getXurWeekly.getResponse().getData().getItems().get(i).getHash());
                                    xurItem.setItemTypeDisplayName(response_getXurWeekly.getResponse().getData().getItems().get(i).getItemTypeDisplayName());

                                    try{ //if item has a cost
                                        xurItem.setCostItemIcon(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getIcon());
                                        xurItem.setCostsQuantity(response_getXurWeekly.getResponse().getData().getItems().get(i).getCost().getQuantity());
                                    }
                                    catch(Exception er){
                                        System.out.println("No cost: "+er);
                                    }

                                    try{
                                        xurItem.setEquippingBlock(response_getXurWeekly.getResponse().getData().getItems().get(i).getEquippingBlock().getDisplayStrings().get(0));
                                    }
                                    catch(Exception e){
                                        System.out.println("No equippingBlock set for item "+ i);
                                    }

                                    //get currency info from manifest //TODO THIS CAN"T RUN HERE, NEED BACKGROUND THREAD
//                                    mCollectablesDAO.getItemByKey(response_getXurWeekly.getResponse().getData().getItems().get(i).getHash())
//                                            .observeOn(Schedulers.io())
//                                            .subscribe(collectableManifest -> {
//
//                                                JsonParser dbParser = new JsonParser();
//                                                JsonObject collectableObj = (JsonObject) dbParser.parse(collectableManifest.getValue());
//
//                                                System.out.println("Item name: " + collectableObj.get("displayProperties").getAsJsonObject().get("name").getAsString());
//                                                System.out.println("Item icon:" + collectableObj.get("displayProperties").getAsJsonObject().get("icon").getAsString());
//                                            });

                                    xurItemsList.add(xurItem);

                                }//end for loop
                                mXurRecyclerAdapter = new XurItemsRecyclerAdapter(getContext(), xurItemsList);
                                mXurRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                mXurRecyclerView.setAdapter(mXurRecyclerAdapter);
                                progressBar.setVisibility(View.GONE);
                            }



                    //then cross reference with Vendor definitions to get currency info for each item
                }, err -> {
                        Snackbar.make(getView(), "Couldn't connect to the server", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .show();
                            progressBar.setVisibility(View.GONE);
                            System.out.println(err.getLocalizedMessage());
                        },
                        this::getLocationBanner
                );
    }

    public void getXurSales(List<InventoryItemModel> itemList, String locationIndex){

        BungieAPI mBungieAPI = new RetrofitHelper().getBungieAPI(baseURL);
    }

    public void getLocationBanner() {
        FactionsDAO mFactionsDAO = AppDatabase.getAppDatabase(getContext()).getFactionsDAO();
        mFactionsDAO.getFactionByKey(theNine)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(faction -> {

                    JsonParser parser = new JsonParser();
                    JsonObject factionObj = (JsonObject) parser.parse(faction.getValue());

                    int vendorIndex = Integer.parseInt(locationIndex);
                    String locationBanner = factionObj.get("vendors").getAsJsonArray().get(vendorIndex).getAsJsonObject().get("backgroundImagePath").getAsString();

                    System.out.println(locationBanner);
                    Picasso.with(getContext())
                            .load(baseURL +"/"+ locationBanner)
                            .into(xurImageBanner);
                });
    }
}

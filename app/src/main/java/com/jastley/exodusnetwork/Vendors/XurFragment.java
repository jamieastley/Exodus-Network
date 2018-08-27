package com.jastley.exodusnetwork.Vendors;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.SnackbarMessage;
import com.jastley.exodusnetwork.Vendors.adapters.XurItemsRecyclerAdapter;
import com.jastley.exodusnetwork.Vendors.fragments.ItemInspectFragment;
import com.jastley.exodusnetwork.Vendors.viewmodels.XurViewModel;
import com.squareup.picasso.Picasso;


import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

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

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private OnFragmentInteractionListener mListener;

    private XurViewModel mViewModel;


    //Xur related stuff
    @BindView(R.id.xur_region_text) TextView xurRegionText;
    @BindView(R.id.xur_world_text) TextView xurWorldText;
    @BindView(R.id.xur_location_banner) ImageView xurImageBanner;
    @BindView(R.id.xur_items_recycler_view) RecyclerView mXurRecyclerView;
    @BindView(R.id.xur_progress_bar) ProgressBar progressBar;
    @BindView(R.id.xur_the_nine_icon) ImageView xurIcon;

    @BindView(R.id.xur_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    XurItemsRecyclerAdapter mXurRecyclerAdapter;


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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_xur, container, false);

        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mXurRecyclerAdapter = new XurItemsRecyclerAdapter(getContext());
        mXurRecyclerView.setAdapter(mXurRecyclerAdapter);
        mXurRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
        mXurRecyclerView.setLayoutAnimation(controller);
        mXurRecyclerView.setNestedScrollingEnabled(false);

        mViewModel = ViewModelProviders.of(getActivity()).get(XurViewModel.class);

        getXurInventory();

        Disposable disposable = mXurRecyclerAdapter.getClickedItem()
                .subscribe(inventoryItemModel -> {
                    mViewModel.setItemDetailsModel(inventoryItemModel);

                    Fragment inspectFragment = ItemInspectFragment.newInstance();

//                    FragmentManager fragmentManager = getFragmentManager();
                    if(getActivity() != null){
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                                .replace(R.id.parent_fragment_frame, inspectFragment, "ITEM_INSPECT")
                                .addToBackStack("ITEM_INSPECTION")
                                .commit();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.refresh_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.refresh_button:
                mSwipeRefresh.setRefreshing(true);
                getXurInventory();
        }

        return super.onOptionsItemSelected(item);
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

        mSwipeRefresh.setOnRefreshListener(() -> {
            mSwipeRefresh.setRefreshing(true);
            getXurInventory();
        });
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
                    + " must implement OnDetailsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        compositeDisposable.dispose();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void getXurInventory() {

        //Get current inventory
        mViewModel.getXurData().observe(this, response_getXurWeekly -> {
            if(response_getXurWeekly.getErrorMessage() != null) {
                SnackbarMessage.getSnackbar(getView(), response_getXurWeekly.getErrorMessage(), BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> getXurInventory())
                        .show();
            }
            else {
                mXurRecyclerAdapter.setXurItems(response_getXurWeekly.getItemList());
            }
            mSwipeRefresh.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        });

        //Get location data
        mViewModel.getXurLocationData().observe(this, xurVendorModel -> {
            if(xurVendorModel.getErrorMessage() != null) {
                SnackbarMessage.getSnackbar(getView(), xurVendorModel.getErrorMessage(), BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .show();
            }
            else {
                Picasso.get()
                        .load(baseURL + xurVendorModel.getLocationBanner())
                        .into(xurImageBanner);

                xurRegionText.setText(xurVendorModel.getRegionText());
                xurWorldText.setText(xurVendorModel.getWorldName());
                xurIcon.setVisibility(View.VISIBLE);
            }
        });

    }

}

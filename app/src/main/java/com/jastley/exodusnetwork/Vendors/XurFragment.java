package com.jastley.exodusnetwork.Vendors;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.jastley.exodusnetwork.Vendors.adapters.XurItemsRecyclerAdapter;
import com.jastley.exodusnetwork.Vendors.fragments.ItemInspectFragment;
import com.jastley.exodusnetwork.Vendors.viewmodels.XurViewModel;
import com.squareup.picasso.Picasso;


import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

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

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private OnFragmentInteractionListener mListener;

    private XurViewModel mViewModel;


    //Xur related stuff
//    @BindView(R.id.xur_region_text) TextView xurRegionText;
//    @BindView(R.id.xur_world_text) TextView xurWorldText;
//    @BindView(R.id.xur_location_banner) ImageView xurImageBanner;
    @BindView(R.id.xur_items_recycler_view) RecyclerView mXurRecyclerView;
//    @BindView(R.id.xur_progress_bar) ProgressBar progressBar;
//    @BindView(R.id.xur_the_nine_icon) ImageView xurIcon;

    @BindView(R.id.xur_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    XurItemsRecyclerAdapter mXurRecyclerAdapter;


    public XurFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static XurFragment newInstance() {
        return new XurFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_xur, container, false);

        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);
        mSwipeRefresh.setRefreshing(true);
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

        DayOfWeek day = DayOfWeek.FRIDAY;


        int range = DayOfWeek.from(day).compareTo(DayOfWeek.TUESDAY);
//        LocalDate ld = LocalDate
        ZonedDateTime zdtNow = ZonedDateTime.now(ZoneOffset.UTC);
        DayOfWeek utcDayNow = zdtNow.getDayOfWeek();
        int hour = zdtNow.getHour();
        int min = zdtNow.getMinute();

        if(utcDayNow.getValue() >= DayOfWeek.FRIDAY.getValue() &&
                utcDayNow.getValue() <= DayOfWeek.TUESDAY.getValue()) {

            String yus = "yus";
        }
        if(DayOfWeek.SATURDAY.getValue() >= DayOfWeek.FRIDAY.getValue() &&
                utcDayNow.getValue() <= DayOfWeek.TUESDAY.getValue()) {

            String yus = "yus";
        }


        mViewModel.getXurData().observe(this, xurData -> {
            if(xurData.getFinalItemList() != null) {
                mXurRecyclerAdapter.setXurItems(xurData.getFinalItemList());
                mSwipeRefresh.setRefreshing(false);
            }
        });

        //Get current inventory
//        mViewModel.getXurData().observe(this, response_getXurWeekly -> {
//            if(response_getXurWeekly.getErrorMessage() != null) {
//                Snackbar.make(getView(), response_getXurWeekly.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
//                        .setAction("Retry", v -> getXurInventory())
//                        .show();
//
//            }
//            else {
//                xurImageBanner.setVisibility(View.VISIBLE);
//                mXurRecyclerAdapter.setXurItems(response_getXurWeekly.getItemList());
//                xurRegionText.setText(response_getXurWeekly.getXurLocation().getRegion());
//                xurWorldText.setText(response_getXurWeekly.getXurLocation().getWorld());
//                xurIcon.setVisibility(View.VISIBLE);
//            }
//            mSwipeRefresh.setRefreshing(false);
////            progressBar.setVisibility(View.GONE);
//        });
//
//        //Get location data
//        mViewModel.getXurLocationData().observe(this, xurVendorModel -> {
//            if(xurVendorModel.getErrorMessage() != null) {
//                Snackbar.make(getView(), xurVendorModel.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
//                        .show();
//            }
//            else {
//                Picasso.get()
//                        .load(baseURL + xurVendorModel.getLocationBanner())
//                        .into(xurImageBanner);
//
//                xurRegionText.setText(xurVendorModel.getRegionText());
//                xurWorldText.setText(xurVendorModel.getWorldName());
//                xurIcon.setVisibility(View.VISIBLE);
//            }
//        });

    }

}

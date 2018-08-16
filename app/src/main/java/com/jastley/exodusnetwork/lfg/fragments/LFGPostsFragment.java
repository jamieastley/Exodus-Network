package com.jastley.exodusnetwork.lfg.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

import com.jastley.exodusnetwork.lfg.NewLFGPostActivity;
import com.jastley.exodusnetwork.lfg.adapters.LFGPostRecyclerAdapter;
import com.jastley.exodusnetwork.lfg.models.SelectedPlayerModel;
import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.lfg.viewmodels.LFGViewModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LFGPostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LFGPostsFragment extends Fragment {

    @BindView(R.id.lfg_recycler_view) RecyclerView mLFGRecyclerView;
    @BindView(R.id.lfg_swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.lfg_progress_bar) ProgressBar mLFGProgressBar;

    private OnFragmentInteractionListener mListener;

    private LFGPostRecyclerAdapter mLFGPostAdapter;
    private SharedPreferences savedPrefs;

    private String displayName = "";
    private View mView;

    private LFGViewModel mViewModel;

    private boolean isNewLFGPost = false;

    @BindView(R.id.fab) FloatingActionButton mFab;
    private boolean isFabVisible = false;

    public LFGPostsFragment() {
        // Required empty public constructor
    }

    public static LFGPostsFragment newInstance(boolean lfgPost) {

        LFGPostsFragment fragment = new LFGPostsFragment();
        Bundle args = new Bundle();
        args.putBoolean("lfgPost", lfgPost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isNewLFGPost = getArguments().getBoolean("lfgPost");
        }
        setHasOptionsMenu(true);
//        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_lfgposts, container, false);

        ButterKnife.bind(this, mView);

        if(isNewLFGPost){
            Snackbar.make(mView, "Post submitted!", Snackbar.LENGTH_LONG)
                    .show();
            isNewLFGPost = false;
        }

        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        showHideFab();

        //Hide FAB when scrolling
        mLFGRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (isFabVisible && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mFab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 ||dy<0 && mFab.isShown())
                    mFab.hide();
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);

            loadLFGPosts();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(LFGViewModel.class);

        initialiseRecyclerView();
        loadLFGPosts();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initialiseRecyclerView() {
        mLFGPostAdapter = new LFGPostRecyclerAdapter();

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
        mLFGRecyclerView.setLayoutAnimation(controller);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());

        mLFGRecyclerView.setLayoutManager(mLinearLayoutManager);
        mLFGRecyclerView.setAdapter(mLFGPostAdapter);

        Disposable disposable = mLFGPostAdapter.onClickSubject.subscribe(onClick -> {
            Toast.makeText(getContext(), onClick.getOwnerMembershipId(), Toast.LENGTH_LONG)
                    .show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.lfg_feed));

        MainActivity activity = (MainActivity)getActivity();
        if(activity != null){

            activity.setActionBarTitle(getString(R.string.lfg_feed));
        }
        TabLayout mTabLayout = getActivity().findViewById(R.id.sliding_tabs);

        mTabLayout.setVisibility(View.GONE);
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
        isNewLFGPost = false;
        mView = null;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Query query;

        switch(item.getItemId()){

//            case R.id.action_refresh:
//                mSwipeRefreshLayout.setRefreshing(true);
//                loadFilteredLFGPosts(null);
//                mLFGPostAdapter.notifyDataSetChanged();
//                break;
//            case R.id.action_filter:
//                break;
//
//            case R.id.filter_none:
//                loadLFGPosts(null);
//                break;
//
//            case R.id.filter_psn:
//                query = mDatabase.child("lfg")
//                        .orderByChild("membershipType")
//                        .equalTo("2");
//                loadLFGPosts(query);
//                break;
//
//            case R.id.filter_xbox:
//                query = mDatabase.child("lfg")
//                        .orderByChild("membershipType")
//                        .equalTo("1");
//                loadLFGPosts(query);
//                break;
//
//            case R.id.filter_bnet:
//                query = mDatabase.child("lfg")
//                        .orderByChild("membershipType")
//                        .equalTo("4");
//                loadLFGPosts(query);
//                break;
        }

        return super.onOptionsItemSelected(item);
    }




    public void loadLFGPosts() {

        mViewModel.getAllFireteams().observe(this, fireteamsList -> {
            mSwipeRefreshLayout.setRefreshing(false);

            if(fireteamsList.getThrowable() != null) {
                Snackbar.make(getView(), fireteamsList.getThrowable().getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> loadLFGPosts())
                        .show();
            }
            else if(fireteamsList.getErrorMessage() != null) {
                Snackbar.make(getView(), fireteamsList.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> loadLFGPosts())
                        .show();
            }
            else if(fireteamsList.getFireteamsList() != null) {
                mLFGPostAdapter.setItems(fireteamsList.getFireteamsList());
            }
        });

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(SelectedPlayerModel playerModel);
    }

    public void showHideFab(){

        savedPrefs = this.getActivity().getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String membershipType = savedPrefs.getString("selectedPlatform", "");
        displayName = savedPrefs.getString("displayName"+membershipType, "");


        if((displayName != "") && (membershipType != "")) {
            mFab.setVisibility(View.VISIBLE);
            isFabVisible = true;

        }
        else {
            mFab.setVisibility(View.INVISIBLE);
            isFabVisible = false;
        }
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        try{
            savedPrefs = this.getActivity().getSharedPreferences("saved_prefs", MODE_PRIVATE);
            String membershipType = savedPrefs.getString("selectedPlatform", "");
            String displayName = savedPrefs.getString("displayName"+membershipType, "");

            Intent intent = new Intent(getActivity().getApplicationContext(), NewLFGPostActivity.class);
            intent.putExtra("displayName", displayName);

            startActivityForResult(intent, 1);

        }
        catch(Exception e){
            Log.d("FAB_CLICK", e.getLocalizedMessage());
            Snackbar.make(getView(), "Couldn't read account data", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void toggleFAButton() {
        mFab.setVisibility(View.INVISIBLE);
    }

    public void showSnackbarMessage(String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_LONG)
                .show();
    }

}

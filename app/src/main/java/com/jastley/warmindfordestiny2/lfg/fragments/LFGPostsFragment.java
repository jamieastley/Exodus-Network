package com.jastley.warmindfordestiny2.lfg.fragments;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.database.*;
import com.jastley.warmindfordestiny2.lfg.NewLFGPostActivity;
import com.jastley.warmindfordestiny2.lfg.RecyclerViewClickListener;
import com.jastley.warmindfordestiny2.lfg.adapters.LFGPostRecyclerAdapter;
import com.jastley.warmindfordestiny2.lfg.holders.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.lfg.models.LFGPost;
import com.jastley.warmindfordestiny2.lfg.models.SelectedPlayerModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;

import java.util.ArrayList;
import java.util.List;

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

    private DatabaseReference mDatabase;
    private LFGPostRecyclerAdapter mLFGPostAdapter;
    private List<LFGPost> lfgPosts = new ArrayList<>();
    private SharedPreferences savedPrefs;

    private Context mContext;
    private String displayName = "";

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
        View rootView = inflater.inflate(R.layout.fragment_lfgposts, container, false);

        ButterKnife.bind(this, rootView);

        if(isNewLFGPost){
            Snackbar.make(rootView, "Post submitted!", Snackbar.LENGTH_LONG)
                    .show();
            isNewLFGPost = false;
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loadLFGPosts(null);

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

            loadLFGPosts(null);
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
        TabLayout mTabLayout = getActivity().findViewById(R.id.inventory_sliding_tabs);

        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
        mContext = null;
        isNewLFGPost = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Query query;

        switch(item.getItemId()){

            case R.id.action_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                loadLFGPosts(null);
                mLFGPostAdapter.notifyDataSetChanged();
                break;
            case R.id.action_filter:
                break;

            case R.id.filter_none:
                loadLFGPosts(null);
                break;

            case R.id.filter_psn:
                query = mDatabase.child("lfg")
                        .orderByChild("membershipType")
                        .equalTo("2");
                loadLFGPosts(query);
                break;

            case R.id.filter_xbox:
                query = mDatabase.child("lfg")
                        .orderByChild("membershipType")
                        .equalTo("1");
                loadLFGPosts(query);
                break;

            case R.id.filter_bnet:
                query = mDatabase.child("lfg")
                        .orderByChild("membershipType")
                        .equalTo("4");
                loadLFGPosts(query);
                break;
        }

        return super.onOptionsItemSelected(item);
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

    public void loadLFGPosts(Query lfgQuery) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSwipeRefreshLayout.setRefreshing(true);

        //load all posts, don't filter
        if(lfgQuery == null){
        lfgQuery = mDatabase.child("lfg")
                .orderByChild("dateTime");

        }
        lfgQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lfgPosts.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    LFGPost post = ds.getValue(LFGPost.class);
                    lfgPosts.add(post);

                }
                mLFGPostAdapter = new LFGPostRecyclerAdapter(mContext, lfgPosts, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position, LFGPostViewHolder holder) {

                        LFGPost selectedPost = new LFGPost();

                        selectedPost.setDisplayName(holder.getDisplayName().getText().toString());
                        selectedPost.setMembershipId(holder.getMembershipId());
                        selectedPost.setMembershipType(holder.getMembershipType());
                        selectedPost.setLightLevel(holder.getLightLevel().getText().toString());
                        selectedPost.setCharacterId(holder.getCharacterId());
                        selectedPost.setClassType(holder.getClassType().getText().toString());
                        selectedPost.setEmblemBackground(holder.getEmblemBackground());
                        selectedPost.setActivityTitle(holder.getActivityTitle().getText().toString());
                        selectedPost.setActivityCheckpoint(holder.getActivityCheckpoint().getText().toString());
                        selectedPost.setHasMic(holder.getHasMic());
                        selectedPost.setDescription(holder.getDescription());

                        Fragment playerFragment;
                        playerFragment = new LFGDetailsFragment();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("clickedPlayer", selectedPost);

                        playerFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.lfg_content_frame, playerFragment)
                                .addToBackStack("lfgStack")
                                .commit();

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                });
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_slide_right_reverse);
                mLFGRecyclerView.setLayoutAnimation(controller);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
                mLinearLayoutManager.setReverseLayout(true);
                mLinearLayoutManager.setStackFromEnd(true);

                mLFGRecyclerView.setLayoutManager(mLinearLayoutManager);
                mLFGRecyclerView.setAdapter(mLFGPostAdapter);

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    }

}

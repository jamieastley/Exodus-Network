package com.jastley.warmindfordestiny2.LFG.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.*;
import com.jastley.warmindfordestiny2.LFG.RecyclerViewClickListener;
import com.jastley.warmindfordestiny2.LFG.adapters.LFGPostRecyclerAdapter;
import com.jastley.warmindfordestiny2.LFG.holders.LFGPostViewHolder;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.LFG.models.SelectedPlayerModel;
import com.jastley.warmindfordestiny2.MainActivity;
import com.jastley.warmindfordestiny2.R;

import java.util.ArrayList;
import java.util.List;

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

    FloatingActionButton mFab;

    public LFGPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lfgposts, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        mFab = ((MainActivity) getActivity()).findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mLFGRecyclerView.setLayoutManager(mLinearLayoutManager);

        //Load LFG posts from Firebase
//        mSwipeRefreshLayout.setRefreshing(true);
        loadLFGPosts(null);

        //Hide FAB when scrolling
        mLFGRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
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
//            mSwipeRefreshLayout.setRefreshing(true);
            loadLFGPosts(null);
            mLFGPostAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.lfg_feed));

        TabLayout mTabLayout = getActivity().findViewById(R.id.inventory_sliding_tabs);

        mTabLayout.setVisibility(View.GONE);
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
                mLFGPostAdapter = new LFGPostRecyclerAdapter(getContext(), lfgPosts, new RecyclerViewClickListener() {
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

        //                mListener.onFragmentInteraction(selectedPlayer);

                        Fragment playerFragment;
                        playerFragment = new LFGDetailsFragment();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("clickedPlayer", selectedPost);

                        playerFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction()
        //                        .setTransition(FragmentTransaction.TR)
                                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.lfg_content_frame, playerFragment)
                                .addToBackStack("lfgStack")
                                .commit();

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                });
                mLFGRecyclerView.setAdapter(mLFGPostAdapter);
                mLFGPostAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

//    @Override
//    public void onBackStackChanged() {
//        if(getActivity().getSupportFragmentManager().getBackStackEntryCount() < 1) {
//            ((MainActivity)getActivity()).hideUpButton();
//        }
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(SelectedPlayerModel playerModel);
    }



}

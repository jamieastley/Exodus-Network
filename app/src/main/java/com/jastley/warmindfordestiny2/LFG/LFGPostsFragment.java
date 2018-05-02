package com.jastley.warmindfordestiny2.LFG;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.*;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.LFG.models.SelectedPlayerModel;
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

    private OnFragmentInteractionListener mListener;

    private DatabaseReference mDatabase;
    private LFGPostRecyclerAdapter mLFGPostAdapter;
    private List<LFGPost> lfgPosts = new ArrayList<>();

    public LFGPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mLFGRecyclerView.setLayoutManager(mLinearLayoutManager);

        //Load LFG posts from Firebase
        loadLFGPosts();
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

    private void loadLFGPosts() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = mDatabase.child("lfg")
                .orderByChild("dateTime");
//                .limitToLast(3);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    LFGPost post = ds.getValue(LFGPost.class);
                    lfgPosts.add(post);

                }
                mLFGPostAdapter = new LFGPostRecyclerAdapter(getContext(), lfgPosts, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position, LFGPostViewHolder holder) {
                        Toast.makeText(getContext(), "Do stuff here", Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        mLFGPostAdapter = new LFGPostRecyclerAdapter(getActivity(), lfgOptions, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position, LFGPostViewHolder holder) {
//
////                SelectedPlayerModel selectedPlayer = new SelectedPlayerModel();
//                LFGPost selectedPost = new LFGPost();
//
//
//                selectedPost.setDisplayName(holder.getDisplayName().getText().toString());
//                selectedPost.setMembershipId(holder.getMembershipId());
//                selectedPost.setMembershipType(holder.getMembershipType());
//                selectedPost.setLightLevel(holder.getLightLevel().getText().toString());
//                selectedPost.setCharacterId(holder.getCharacterId());
//                selectedPost.setClassType(holder.getClassType().getText().toString());
//                selectedPost.setEmblemBackground(holder.getEmblemBackground());
//                selectedPost.setActivityTitle(holder.getActivityTitle().getText().toString());
//                selectedPost.setActivityCheckpoint(holder.getActivityCheckpoint().getText().toString());
//                selectedPost.setHasMic(holder.getHasMic());
//                selectedPost.setDescription(holder.getDescription());
//
////                mListener.onFragmentInteraction(selectedPlayer);
//
//
//
//                Fragment playerFragment;
//                playerFragment = new LFGDetailsFragment();
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("clickedPlayer", selectedPost);
//
//                playerFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//
//                fragmentManager.beginTransaction()
////                        .setTransition(FragmentTransaction.TR)
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .replace(R.id.lfg_content_frame, playerFragment)
//                        .addToBackStack("lfgStack")
//                        .commit();
//
////
////
////                Toast.makeText(MainActivity.this, holder.getDisplayName().getText() + " clicked", Toast.LENGTH_SHORT).show();
//
////                Intent lfgFeed = new Intent(this, MainActivity.class);
////                Intent intent = new Intent(getBaseContext(), LFGPlayerDetails.class);
////                intent.putExtra("displayName", holder.getDisplayName().getText());
////                intent.putExtra("membershipId", holder.getMembershipId());
////                intent.putExtra("characterId", holder.getCharacterId());
////                intent.putExtra("classType", holder.getClassType().getText());
////                intent.putExtra("emblemBackground", holder.getEmblemBackground());
////                startActivity(intent);
//
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        });
//        mLFGRecyclerView.setAdapter(mLFGPostAdapter);

//        mLFGPostAdapter.startListening();

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

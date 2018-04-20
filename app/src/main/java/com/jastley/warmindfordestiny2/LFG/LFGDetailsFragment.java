package com.jastley.warmindfordestiny2.LFG;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.LFG.models.SelectedPlayerModel;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.Response_GetGroupsForMember;
import com.jastley.warmindfordestiny2.api.Response_GetHistoricalStatsAccount;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jastley.warmindfordestiny2.api.apiKey.apiKey;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LFGDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LFGDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LFGDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String baseURL = "https://www.bungie.net";
    LFGPost receivedPlayerClick;
    @BindView(R.id.lfg_details_display_name) TextView displayName;
    @BindView(R.id.lfg_details_emblem_background) ImageView emblemBackground;
    @BindView(R.id.lfg_details_light) TextView lightLevel;
    @BindView(R.id.lfg_details_class_type) TextView classType;
    @BindView(R.id.lfg_details_description) TextView description;
    @BindView(R.id.lfg_details_group_name) TextView groupName;
    @BindView(R.id.lfg_details_microphone_icon) ImageView micIcon;
    @BindView(R.id.lfg_details_platform_icon) ImageView platformIcon;
    @BindView(R.id.lfg_details_play_time) TextView playTime;
    @BindView(R.id.lfg_details_lifespan) TextView lifeSpan;
    @BindView(R.id.lfg_details_kdr) TextView kdRatio;
    @BindView(R.id.lfg_details_activity_title) TextView activityTitle;
    @BindView(R.id.lfg_details_activity_checkpoint) TextView activityCheckpoint;
    @BindView(R.id.stats_values_progress_bar) ProgressBar statsValuesProgress;

//    @BindView(R.id.lfg_details_emblem_icon) ImageView emblemIcon;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LFGDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LFGDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LFGDetailsFragment newInstance(String param1, String param2) {
        LFGDetailsFragment fragment = new LFGDetailsFragment();
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
            Bundle bundle = getArguments();
            receivedPlayerClick = bundle.getParcelable("clickedPlayer");

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lfgdetails, container, false);

//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Test");

//        SelectedPlayerModel clicked = mListener.onFragmentInteraction();

//        Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });

        ButterKnife.bind(this, view);

        displayName.setText(receivedPlayerClick.getDisplayName());
        lightLevel.setText(receivedPlayerClick.getLightLevel());
        classType.setText(receivedPlayerClick.getClassType());
        activityTitle.setText(receivedPlayerClick.getActivityTitle());
        activityCheckpoint.setText(receivedPlayerClick.getActivityCheckpoint());

        if(receivedPlayerClick.isHasMic()){
            micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_on_24dp));
        }
        else {
            micIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_mic_off_24dp));
        }

        switch (receivedPlayerClick.getMembershipType()) {

            case "1":
                platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_xbl));
                break;
            case "2":
                platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_psn));
                break;
            case "4":
                platformIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_blizzard));
                break;
        }

        String membershipId = receivedPlayerClick.getMembershipId();
        String membershipType = receivedPlayerClick.getMembershipType();

        Picasso.with(getContext())
                .load(baseURL+receivedPlayerClick.getEmblemBackground())
                .into(emblemBackground);

        getHistoricalStatsAccount(membershipType, membershipId);

//        getClanData(membershipType, membershipId);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
////            mListener.onFragmentInteraction();
//        }
//    }

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

//    @Override
//    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//
//
//        receivedPlayerClick = playerModel;
//    }

//    @Override
//    public void onFragmentInteraction(SelectedPlayerModel playerModel) {
//        Toast.makeText(getActivity(), playerModel.getEmblemBackground(), Toast.LENGTH_SHORT).show();
//    }

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


    }

    public void getHistoricalStatsAccount(final String membershipType, final String membershipId) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("X-API-Key", apiKey);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();

        BungieAPI bungieClient = retrofit.create(BungieAPI.class);
        Call<Response_GetHistoricalStatsAccount> getHistoricalStatsAccountCall = bungieClient.getHistoricalStatsAccount(
                membershipType,
                membershipId
        );

        getHistoricalStatsAccountCall.enqueue(new Callback<Response_GetHistoricalStatsAccount>() {
            @Override
            public void onResponse(Call<Response_GetHistoricalStatsAccount> call, Response<Response_GetHistoricalStatsAccount> response) {

                try{
                    playTime.setText(response.body().getResponse().getMergedAllCharacters().getMerged().getAccountAllTime().getSecondsPlayed().getBasic().getDisplayValue());
                }
                catch(Exception e){
                    playTime.setText("-");
                }

                try{
                    lifeSpan.setText(response.body().getResponse().getMergedAllCharacters().getResults().getAllPvP().getAllTime().getAverageLifespan().getBasic().getDisplayValue());
                }
                catch(Exception e){
                    lifeSpan.setText("-");
                }

                try{
                    kdRatio.setText(response.body().getResponse().getMergedAllCharacters().getResults().getAllPvP().getAllTime().getKillsDeathsRatio().getBasic().getDisplayValue());
                }
                catch(Exception e){
                    kdRatio.setText("-");
                }

                statsValuesProgress.setVisibility(View.GONE);

                getClanData(membershipType, membershipId);

            }

            @Override
            public void onFailure(Call<Response_GetHistoricalStatsAccount> call, Throwable t) {

            }
        });

    }


    public void getClanData(String membershipType, String membershipId) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("X-API-Key", apiKey);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.build();

        BungieAPI bungieClient = retrofit.create(BungieAPI.class);
        Call<Response_GetGroupsForMember> getGroupsForMemberCall = bungieClient.getClanData(
                membershipType,
                membershipId
        );

        getGroupsForMemberCall.enqueue(new Callback<Response_GetGroupsForMember>() {
            @Override
            public void onResponse(Call<Response_GetGroupsForMember> call, Response<Response_GetGroupsForMember> response) {

                try{
                    int groupCount = response.body().getResponse().getTotalResults();
                    groupName.setText(response.body().getResponse().getResults().get(groupCount).getGroup().getName());
                }
                catch(Exception e) {
                    groupName.setText("");
                }
            }

            @Override
            public void onFailure(Call<Response_GetGroupsForMember> call, Throwable t) {

            }
        });

    }
















}

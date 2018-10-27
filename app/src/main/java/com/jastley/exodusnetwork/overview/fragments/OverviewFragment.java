package com.jastley.exodusnetwork.overview.fragments;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.overview.adapters.EmblemRecyclerAdapter;
import com.jastley.exodusnetwork.overview.viewmodels.OverviewViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.Definitions.getInfamyRank;
import static com.jastley.exodusnetwork.Definitions.getValorGloryRank;

public class OverviewFragment extends Fragment {

    private OverviewViewModel mViewModel;
//    @BindView(R.id.overview_swipe_refresh) SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.overview_valor_progress) ProgressBar valorProgressBar;
    @BindView(R.id.overview_glory_progress) ProgressBar gloryProgressBar;
    @BindView(R.id.overview_infamy_progress) ProgressBar infamyProgressBar;

    @BindView(R.id.valor_rank_icon) ImageView valorRankIcon;
    @BindView(R.id.valor_rank) TextView valorRank;
    @BindView(R.id.valor_current_progress) TextView valorCurrentProgress;

    @BindView(R.id.glory_rank_icon) ImageView gloryRankIcon;
    @BindView(R.id.glory_rank) TextView gloryRank;
    @BindView(R.id.glory_current_progress) TextView gloryCurrentProgress;

    @BindView(R.id.gambit_rank_icon) ImageView infamyRankIcon;
    @BindView(R.id.infamy_rank) TextView infamyRank;
    @BindView(R.id.infamy_current_progress) TextView infamyCurrentProgress;

    @BindView(R.id.overview_character_recycler_view) RecyclerView charactersRecyclerView;
    private EmblemRecyclerAdapter emblemRecyclerAdapter;


    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.overview_fragment, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        swipeRefresh.setRefreshing(true);
        initialiseRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OverviewViewModel.class);

        mViewModel.getProfileOverview();

        displayProfileOverview();
    }

    private void initialiseRecyclerView() {
        emblemRecyclerAdapter = new EmblemRecyclerAdapter();
        charactersRecyclerView.setNestedScrollingEnabled(false);
        charactersRecyclerView.setAdapter(emblemRecyclerAdapter);
        charactersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void displayProfileOverview() {

        mViewModel.getValorLiveData().observe(this, valor -> {

//            swipeRefresh.setRefreshing(false);

            valorCurrentProgress.setText(String.valueOf(valor.getCurrentProgress()));
            valorProgressBar.setMax(valor.getNextLevelAt());
            animateProgress(valorProgressBar, valor.getProgressToNextLevel());


            valorRank.setText(getValorGloryRank(valor.getLevel()));
            switch(valor.getLevel()) {
                case 0:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_no_rank));
                    break;
                case 1:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_brave));
                    break;
                case 2:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_heroic));
                    break;
                case 3:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_fabled));
                    break;
                case 4:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_mythic));
                    break;
                case 5:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_legend));
                    break;
                case 6:
                    valorRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_valor_legend));
                    break;
            }
        });

        mViewModel.getGloryLiveData().observe(this, glory -> {
            gloryCurrentProgress.setText(String.valueOf(glory.getCurrentProgress()));
            gloryProgressBar.setMax(glory.getNextLevelAt());
            animateProgress(gloryProgressBar, glory.getProgressToNextLevel());

            gloryRank.setText(getValorGloryRank(glory.getLevel()));
            switch(glory.getLevel()) {
                case 0:

                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_no_rank));
                    break;
                case 1:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_brave));
                    break;
                case 2:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_heroic));
                    break;
                case 3:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_fabled));
                    break;
                case 4:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_mythic));
                    break;
                case 5:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_legend));
                    break;
                case 6:
                    gloryRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_glory_legend));
                    break;
            }
        });


        mViewModel.getInfamyLiveData().observe(this, infamy -> {

            infamyCurrentProgress.setText(String.valueOf(infamy.getCurrentProgress()));
            infamyProgressBar.setMax(infamy.getNextLevelAt());
            animateProgress(infamyProgressBar, infamy.getProgressToNextLevel());

            infamyRank.setText(getInfamyRank(infamy.getLevel()));
            int level = infamy.getLevel();

            if(level <= 2) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_no_rank));
            }
            else if(level <= 5) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_gambit_brave));
            }
            else if(level <= 8) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_gambit_heroic));
            }
            else if(level <= 11) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_gambit_fabled));
            }
            else if(level <= 14) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_gambit_mythic));
            }
            else if(level <= 16) {
                infamyRankIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_gambit_legend));
            }
        });


        mViewModel.getCharacterData().observe(this, characterData -> {
            emblemRecyclerAdapter.setItemsList(characterData);
        });
    }

    private void animateProgress(ProgressBar progressBar, int value) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), value);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.setAutoCancel(true);
    }

}

package com.jastley.exodusnetwork.settings;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class SettingsRecyclerAdapter extends RecyclerView.Adapter<SettingsViewHolder> {

    private List<SettingsModel> settingsList = new ArrayList<>();
    private Context context;

    private PublishSubject<SettingsViewHolder> onClickSubject = PublishSubject.create();

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item_row, parent, false);

        final SettingsViewHolder settingsViewHolder = new SettingsViewHolder(mView);

        settingsViewHolder.itemView.setOnClickListener(view -> onClickSubject.onNext(settingsViewHolder));

        return new SettingsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.setTitle(settingsList.get(position).getTitle());
        holder.setDetails(settingsList.get(position).getMessage());

        final SettingsModel setting = settingsList.get(position);

        //holder.itemView.setOnClickListener(view -> onClickSubject.onNext(setting));
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public void setSettingsList(List<SettingsModel> list) {
        this.settingsList = list;
        notifyDataSetChanged();
    }

    public PublishSubject<SettingsViewHolder> getOnClickSubject() {
        return onClickSubject;
    }


}

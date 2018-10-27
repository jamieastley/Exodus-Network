package com.jastley.exodusnetwork.overview.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.overview.viewholders.EmblemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EmblemRecyclerAdapter extends RecyclerView.Adapter<EmblemViewHolder> {

    private List<Response_GetAllCharacters.CharacterData> characterList = new ArrayList<>();

    @NonNull
    @Override
    public EmblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmblemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emblem_container_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmblemViewHolder holder, int position) {
        holder.setCharacterLevel(characterList.get(position).getBaseCharacterLevel());
        holder.setLightLevel(characterList.get(position).getLight());
        holder.setEmblemBackground(characterList.get(position).getEmblemBackgroundPath());
        holder.setClassName(characterList.get(position).getClassType());
        holder.setRaceGenderName(characterList.get(position).getRaceType(),
                                characterList.get(position).getGenderType());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemsList(List<Response_GetAllCharacters.CharacterData> list) {
        this.characterList = list;
        notifyDataSetChanged();
    }


}

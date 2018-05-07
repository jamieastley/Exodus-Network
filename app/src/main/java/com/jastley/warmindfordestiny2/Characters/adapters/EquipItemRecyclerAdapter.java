package com.jastley.warmindfordestiny2.Characters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.warmindfordestiny2.Characters.holders.EquipItemViewHolder;
import com.jastley.warmindfordestiny2.Characters.interfaces.EquipSelectListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class EquipItemRecyclerAdapter extends RecyclerView.Adapter<EquipItemViewHolder> {

    private Context mContext;
    private List<CharacterDatabaseModel> mCharacters;
    private EquipSelectListener mListener;

    public EquipItemRecyclerAdapter(Context context,
                                    List<CharacterDatabaseModel> characters,
                                    EquipSelectListener listener) {
        this.mContext = context;
        this.mCharacters = characters;
        this.mListener = listener;
    }

    @Override
    public EquipItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_emblem_equip_row, parent, false);

        final EquipItemViewHolder mEquipViewHolder = new EquipItemViewHolder(mView);

        mEquipViewHolder.itemView.setOnClickListener(view -> {
            mListener.onEquipSelect(mView, mEquipViewHolder.getAdapterPosition(), mEquipViewHolder);
        });

        return null;
    }

    @Override
    public void onBindViewHolder(EquipItemViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }
}

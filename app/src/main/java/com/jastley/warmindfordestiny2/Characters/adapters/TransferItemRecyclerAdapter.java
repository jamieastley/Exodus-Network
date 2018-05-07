package com.jastley.warmindfordestiny2.Characters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.warmindfordestiny2.Characters.holders.TransferItemViewHolder;
import com.jastley.warmindfordestiny2.Characters.interfaces.TransferSelectListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class TransferItemRecyclerAdapter extends RecyclerView.Adapter<TransferItemViewHolder> {

    private Context mContext;
    private List<CharacterDatabaseModel> mCharacters;
    private TransferSelectListener mListener;

    public TransferItemRecyclerAdapter(Context context,
                                       List<CharacterDatabaseModel> characters,
                                       TransferSelectListener listener) {
        this.mContext = context;
        this.mCharacters = characters;
        this.mListener = listener;
    }

    @Override
    public TransferItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_emblem_transfer_row, parent, false);

        final TransferItemViewHolder mTransferItemVH = new TransferItemViewHolder(mView);

        mTransferItemVH.itemView.setOnClickListener(view -> {
            mListener.onTransferSelect(mView, mTransferItemVH.getAdapterPosition(), mTransferItemVH);
        });

        return mTransferItemVH;
    }

    @Override
    public void onBindViewHolder(final TransferItemViewHolder holder, int position) {
        holder.setEmblemBackground(mCharacters.get(position).getEmblemBackground(), mContext);
        holder.setCharacterId(mCharacters.get(position).getCharacterId());

    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }
}

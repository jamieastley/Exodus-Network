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

    //UI manipulation
    private int tabIndex;
    private int mPosition;
    private String vaultCharacterId;

    public TransferItemRecyclerAdapter(Context context,
                                       int index,
                                       String vaultCharId,
                                       List<CharacterDatabaseModel> characters,
                                       TransferSelectListener listener) {
        this.mContext = context;
        this.tabIndex = index;
        this.vaultCharacterId = vaultCharId;
        this.mCharacters = characters;
        this.mListener = listener;

    }

    @Override
    public TransferItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_emblem_transfer_row, parent, false);

        System.out.println("Class type: " + mCharacters.get(mPosition).getClassType());
        System.out.println("onCreateVH tabIndex: " + tabIndex);
        System.out.println("adapter mPosition: " + mPosition);
//        if(mPosition == tabIndex){
//            mView.setEnabled(false);
//            mView.setAlpha(0.3f);
//        }

        final TransferItemViewHolder mTransferItemVH = new TransferItemViewHolder(mView);

        mTransferItemVH.itemView.setOnClickListener(view -> {
            mListener.onTransferSelect(mView, mTransferItemVH.getAdapterPosition(), mTransferItemVH);
        });

        return mTransferItemVH;
    }

    @Override
    public void onBindViewHolder(final TransferItemViewHolder holder, int position) {

//        this.mPosition = position;
        System.out.println("VH position "+position+", class "+mCharacters.get(position).getClassType());

        //if not vault position, set row with character details
        if(!mCharacters.get(position).getClassType().equals("vault")){
            holder.setEmblemBackground(mCharacters.get(position).getEmblemBackground(), mContext);
            holder.setCharacterId(mCharacters.get(position).getCharacterId());
            holder.setLightLevel(mCharacters.get(position).getLightLevel());
            holder.setmBaseLevel(mCharacters.get(position).getBaseCharacterLevel());
            holder.setClassType(mCharacters.get(position).getClassType());
            holder.setVaultCharacterId(vaultCharacterId);
        }
        else{ //still need a characterId to transfer to vault
//            holder.setCharacterId(mCharacters.get(position-1).getCharacterId());
            holder.setClassType("vault");
        }

        //Disable transferring item to character it already exists on
        if(position == tabIndex){
            holder.setDisabled();
        }
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

package com.jastley.exodusnetwork.Inventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.exodusnetwork.Inventory.holders.EquipItemViewHolder;
import com.jastley.exodusnetwork.Inventory.interfaces.EquipSelectListener;
import com.jastley.exodusnetwork.Inventory.models.CharacterDatabaseModel;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

import java.util.List;

public class EquipItemRecyclerAdapter extends RecyclerView.Adapter<EquipItemViewHolder> {

    private Context mContext;
    private List<CharacterDatabaseModel> mCharacters;
    private EquipSelectListener mListener;
    private InventoryItemModel mSelectedItem;
    private int mTabIndex;

    public EquipItemRecyclerAdapter(Context context,
                                    InventoryItemModel item,
                                    int index,
                                    List<CharacterDatabaseModel> characters,
                                    EquipSelectListener listener) {
        this.mContext = context;
        this.mSelectedItem = item;
        this.mTabIndex = index;
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

        return mEquipViewHolder;
    }

    @Override
    public void onBindViewHolder(EquipItemViewHolder holder, int position) {

        holder.setEmblemIcon(mCharacters.get(position).getEmblemIcon(), mContext);
        holder.setCharacterId(mCharacters.get(position).getCharacterId());
        holder.setCharacterLevel(mCharacters.get(position).getBaseCharacterLevel());
        holder.setClassType(mCharacters.get(position).getClassType());

        if(mSelectedItem.getCanEquip()) {
            holder.setDisabled();
        }
        //if user selected characters' sub-class
        else if (mSelectedItem.getSlot() == 10) {
            //if not equipped, only allow equipping for the character that the sub-class is for
            if(position != mTabIndex){
                holder.setDisabled();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

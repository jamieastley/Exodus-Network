package com.jastley.exodusnetwork.Inventory.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.exodusnetwork.Inventory.holders.EquipItemViewHolder;
import com.jastley.exodusnetwork.Inventory.interfaces.EquipSelectListener;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;

import java.util.ArrayList;
import java.util.List;

import static com.jastley.exodusnetwork.Definitions.itemUnequippable;

public class EquipItemRecyclerAdapter extends RecyclerView.Adapter<EquipItemViewHolder> {

    private Context mContext;
//    private List<CharacterDatabaseModel> mCharacters;
    private List<Response_GetAllCharacters.CharacterData> characterList = new ArrayList<>();
    private EquipSelectListener mListener;
    private InventoryItemModel selectedItem;
    private int mTabIndex;


//    public EquipItemRecyclerAdapter(Context context,
//                                    InventoryItemModel item,
//                                    int index,
//                                    List<CharacterDatabaseModel> characters,
//                                    EquipSelectListener listener) {
//        this.mContext = context;
//        this.mSelectedItem = item;
//        this.mTabIndex = index;
//        this.mCharacters = characters;
//        this.mListener = listener;
//    }


    public EquipItemRecyclerAdapter(int mTabIndex,
                                    InventoryItemModel item,
                                    EquipSelectListener listener) {
        this.mTabIndex = mTabIndex;
        this.selectedItem = item;
        this.mListener = listener;
    }

    @Override
    public EquipItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_emblem_equip_row, parent, false);

        this.mContext = parent.getContext();

        final EquipItemViewHolder mEquipViewHolder = new EquipItemViewHolder(mView);

        mEquipViewHolder.itemView.setOnClickListener(view -> {
            mListener.onEquipSelect(mView, mEquipViewHolder.getAdapterPosition(), mEquipViewHolder);
        });

        return mEquipViewHolder;
    }

    @Override
    public void onBindViewHolder(EquipItemViewHolder holder, int position) {

        if(!characterList.get(position).getClassType().toLowerCase().equals("vault")){
            holder.setEmblemIcon(characterList.get(position).getEmblemPath(), mContext);
    //        holder.setCharacterId(mCharacters.get(position).getCharacterId());
    //        holder.setCharacterLevel(mCharacters.get(position).getBaseCharacterLevel());

            holder.setClassType(characterList.get(position).getClassType(), mContext);

            if((selectedItem.getCannotEquipReason() & itemUnequippable) != 0) {
                holder.setDisabled();
            }
            else if(selectedItem.getIsEquipped()) {
                holder.setDisabled();
            }
            //Class item specific equip check
            else if(!selectedItem.getClassType().equals("3")) { //3 = not restricted to a class
                if(!selectedItem.getClassType().equals(characterList.get(position).getClassType()))
                holder.setDisabled();
            }
    //        //if user selected characters' sub-class
            else if (selectedItem.getSlot() == 10) {
                //if not equipped, only allow equipping for the character that the sub-class is for
                if(position != mTabIndex){
                    holder.setDisabled();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCharacterList(List<Response_GetAllCharacters.CharacterData> list) {
        //Last index is always vault, can't equip on vault so remove it for this adapter
//        int size = list.size();
//        list.remove(size -1);
        this.characterList = list;
        notifyDataSetChanged();
    }
}

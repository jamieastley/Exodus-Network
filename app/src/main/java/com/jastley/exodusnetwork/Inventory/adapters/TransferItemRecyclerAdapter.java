package com.jastley.exodusnetwork.Inventory.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jastley.exodusnetwork.Inventory.holders.TransferItemViewHolder;
import com.jastley.exodusnetwork.Inventory.interfaces.TransferSelectListener;
import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;

import java.util.ArrayList;
import java.util.List;

import static com.jastley.exodusnetwork.Definitions.itemUnequippable;

public class TransferItemRecyclerAdapter extends RecyclerView.Adapter<TransferItemViewHolder> {

    private Context mContext;
    private List<Response_GetAllCharacters.CharacterData> characterList = new ArrayList<>();
    private TransferSelectListener mListener;

    //UI manipulation
    private int mTabIndex;
    private String vaultCharacterId;
    private InventoryItemModel selectedItem;

    public TransferItemRecyclerAdapter(int index,
                                       InventoryItemModel item,
                                       TransferSelectListener listener)
//            Context context,
//                                       InventoryItemModel selectedItem,
//                                       int index,
//                                       String vaultCharId,
//                                       List<CharacterDatabaseModel> characters,
//                                       TransferSelectListener listener
//    )
 {
//        this.mContext = context;
//        this.mSelectedItem = selectedItem;
        this.mTabIndex = index;
        this.selectedItem = item;
        this.mListener = listener;
//        this.vaultCharacterId = vaultCharId;
//        this.mCharacters = characters;
//        this.mListener = listener;

    }

    @Override
    public TransferItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_emblem_transfer_row, parent, false);

        final TransferItemViewHolder mTransferItemVH = new TransferItemViewHolder(mView);
        this.mContext = parent.getContext();

        mTransferItemVH.itemView.setOnClickListener(view -> {
            mListener.onTransferSelect(mView, mTransferItemVH.getAdapterPosition(), mTransferItemVH);
        });

        return mTransferItemVH;
    }

    @Override
    public void onBindViewHolder(final TransferItemViewHolder holder, int position) {

//        this.mPosition = position;
        System.out.println("VH position "+position+", class "+characterList.get(position).getClassType());

        //if not vault position, set row with character details
        if(!characterList.get(position).getClassType().equals("vault")){
//            holder.setEmblemBackground(mCharacters.get(position).getEmblemBackground(), mContext);
//            holder.setCharacterId(mCharacters.get(position).getCharacterId());
//            holder.setLightLevel(mCharacters.get(position).getLightLevel());
//            holder.setmBaseLevel(mCharacters.get(position).getBaseCharacterLevel());
            holder.setEmblemIcon(characterList.get(position).getEmblemPath(), mContext);
            holder.setClassType(characterList.get(position).getClassType());
            holder.setVaultCharacterId(vaultCharacterId);
        }
        else{ //still need a characterId to transfer to vault
            holder.setClassType("vault");
        }


        if(selectedItem.getSlot() != 0){ //not a lost item in Postmaster
            //Disable transferring item to character it already exists on
            if(position == mTabIndex){
                holder.setDisabled();
            }
            //Cannot transfer items that are equipped to a character
            else if(selectedItem.getIsEquipped()) {
                holder.setDisabled();
            }
            //if user selected characters' sub-class
            else if (selectedItem.getSlot() == 10) {
                //Can't transfer subclasses
                holder.setDisabled();
            }
            else if((selectedItem.getCannotEquipReason() & itemUnequippable) != 0) {
                holder.setDisabled();
            }
//            else if(!selectedItem.getCanEquip()) {
//                holder.setDisabled();
//            }
        }
        else {
            if(position != mTabIndex) {
                holder.setDisabled();
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

    public void setCharacterList(List<Response_GetAllCharacters.CharacterData> characters) {
        characterList = characters;
        notifyDataSetChanged();
    }
}

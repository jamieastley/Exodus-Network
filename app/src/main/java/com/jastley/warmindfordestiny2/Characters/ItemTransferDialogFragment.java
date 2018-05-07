package com.jastley.warmindfordestiny2.Characters;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.Characters.adapters.EquipItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.adapters.TransferItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.interfaces.TransferSelectListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.R;

import java.util.List;

public class ItemTransferDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.transfer_recycler_view) RecyclerView mTransferRecyclerView;
    @BindView(R.id.equip_recycler_view) RecyclerView mEquipRecyclerView;

    TransferItemRecyclerAdapter mTransferAdapter;
    EquipItemRecyclerAdapter mEquipAdapter;
    List<CharacterDatabaseModel> mCharacters;
    TransferSelectListener mListener;
    InventoryItemModel selectedItem;



    public ItemTransferDialogFragment() {
        super();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_item_transfer_modal, null);

        ButterKnife.bind(this, contentView);


//        CharacterInventoryActivity activity = new CharacterInventoryActivity();
//        mCharacters = activity.getCharactersList();
//
//        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(), mCharacters);
//
//        mTransferRecyclerView.setAdapter(mTransferAdapter);

//        mTransferRecyclerView = contentView.findViewById(R.id.transfer_recycler_view);
//
//        CharacterInventoryActivity activity = (CharacterInventoryActivity) getActivity();
//        mCharacters = activity.getCharactersList();
//
//        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(), mCharacters);
//
//        mTransferRecyclerView.setAdapter(mTransferAdapter);



//        dialog.setTitle(R.string.transferItem);
        dialog.setContentView(contentView);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        selectedItem = args.getParcelable("selectedItem");

        return super.onCreateDialog(savedInstanceState);
    }

//
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        //Get list of users' characters
        CharacterInventoryActivity activity = (CharacterInventoryActivity) getActivity();
        mCharacters = activity.getCharactersList();


//        Transfer item row section
        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(), mCharacters, (view, position, holder) -> {

            //transfer item to character/vault
            Toast.makeText(getContext(), holder.getCharacterId(), Toast.LENGTH_SHORT).show();
        });


        //Equip item methods

        mEquipRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mEquipAdapter = new EquipItemRecyclerAdapter(getContext(), mCharacters, (view, position, holder) -> {

            //Equip item on character
            Toast.makeText(getContext(), holder.getCharacterId(), Toast.LENGTH_SHORT).show();
        });


        mTransferRecyclerView.setAdapter(mTransferAdapter);
        mEquipRecyclerView.setAdapter(mEquipAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);

//        return rootView;
    }
}

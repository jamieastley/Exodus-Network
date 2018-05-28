package com.jastley.warmindfordestiny2.Characters.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.Characters.adapters.EquipItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.adapters.TransferItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.interfaces.SuccessListener;
import com.jastley.warmindfordestiny2.Characters.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Characters.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.Definitions;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.api.models.EquipItemRequestBody;
import com.jastley.warmindfordestiny2.api.models.TransferItemRequestBody;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class ItemTransferDialogFragment extends BottomSheetDialogFragment {

    //Equip/transfer
    @BindView(R.id.transfer_recycler_view) RecyclerView mTransferRecyclerView;
    @BindView(R.id.equip_recycler_view) RecyclerView mEquipRecyclerView;

    //Selected item
    @BindView(R.id.selected_item_image) ImageView itemImage;
    @BindView(R.id.primary_stat_value) TextView primaryStatValue;
    @BindView(R.id.selected_item_name) TextView itemName;
    @BindView(R.id.selected_item_type) TextView itemType;
    @BindView(R.id.item_modifier_icon) ImageView damageTypeIcon;

    TransferItemRecyclerAdapter mTransferAdapter;
    EquipItemRecyclerAdapter mEquipAdapter;
    List<CharacterDatabaseModel> mCharacters;
    InventoryItemModel selectedItem;

    TransferItemRequestBody mTransferBody;
    EquipItemRequestBody mEquipBody;


    private BungieAPI mBungieApi;
    private int mTabIndex;
    private OnFragmentInteractionListener mListener;
    static SuccessListener mSuccessListener;


    public static ItemTransferDialogFragment newInstance(InventoryItemModel selectedItem,
                                                         int tabIndex,
                                                         List<CharacterDatabaseModel> characterList,
                                                         SuccessListener listener) {

        ItemTransferDialogFragment fragment = new ItemTransferDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("selectedItem", selectedItem);
        args.putParcelableArrayList("characterList", (ArrayList<? extends Parcelable>) characterList);
        args.putInt("tabIndex", tabIndex);
        mSuccessListener = listener;
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {

        View contentView = View.inflate(getContext(), R.layout.fragment_item_transfer_modal, null);
        ButterKnife.bind(this, contentView);

        dialog.setContentView(contentView);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        selectedItem = args.getParcelable("selectedItem");
        mTabIndex = args.getInt("tabIndex");
        mCharacters = args.getParcelableArrayList("characterList");

        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBungieApi = RetrofitHelper.getAuthBungieAPI(getContext(), baseURL);

        //Selected item details
        itemName.setText(selectedItem.getItemName());
        primaryStatValue.setText(selectedItem.getPrimaryStatValue());
        itemType.setText(selectedItem.getItemTypeDisplayName());
        if(selectedItem.getDamageType() != null){

            switch(selectedItem.getDamageType()){

                //set damageType icon
                case(Definitions.dmgTypeKinetic):
                    //TODO - maybe
                    break;

                case(Definitions.dmgTypeArc):
                    damageTypeIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.modifier_arc));
                    break;

                case(Definitions.dmgTypeThermal):
                    damageTypeIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.modifier_solar));
                    break;

                case(Definitions.dmgTypeVoid):
                    damageTypeIcon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.modifier_void));
                    break;

                case(Definitions.dmgTypeRaid):
                    //TODO - maybe
                    break;

            }
        }
        Picasso.with(getContext())
                .load(baseURL + selectedItem.getItemIcon())
                .placeholder(R.drawable.missing_icon_d2)
                .into(itemImage);


//        Transfer item row section
        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(),
                                                        mTabIndex, //which tab is active
                                                        selectedItem.getVaultCharacterId(), //the characterId of the selected tab
                                                        mCharacters,
                                                        (view, position, holder) -> {

            //TODO: display loadingFragment, block UI interaction
            dismiss();
            mSuccessListener.inProgress();

            String className = holder.getClassType();
            System.out.println("position: " + position);
            if(holder.getClassType().toLowerCase().equals("vault")){

                //Send item to vault
                mTransferBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        true,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(mTabIndex).getCharacterId()
                        );
            }
            else {

                //send to character
                mTransferBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        false,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(position).getCharacterId()
                );
            }


            mBungieApi.transferItem(mTransferBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        if(!response.getErrorCode().equals("1")){
                            Log.d("TRANSFER_ERROR: "+response.getErrorCode(), response.getMessage());
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(),
                                                            false,
                                                                response.getErrorCode()+": "+response.getMessage());
                        }
                        else {
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), true, className);
                        }

                    }, throwable -> {
                        Log.d("TRANSFER_EQUIP_ERROR", throwable.getLocalizedMessage() +", message: "+ throwable.getMessage());
                        mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), false, throwable.getMessage());
                    });

        });


        //Equip item methods
        mEquipRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mEquipAdapter = new EquipItemRecyclerAdapter(getContext(), mCharacters, (view, position, holder) -> {

            dismiss();
            mSuccessListener.inProgress();

            //Equip item on character
            mEquipBody = new EquipItemRequestBody(
                selectedItem.getItemInstanceId(),
                mCharacters.get(position).getCharacterId(),
                mCharacters.get(position).getMembershipType()
            );
            mBungieApi.equipItem(mEquipBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        if(!response.getErrorCode().equals("1")){
                            Log.d("EQUIP_ERROR: "+response.getErrorCode(), response.getMessage());
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(),
                                    false,
                                    response.getErrorCode()+": "+response.getMessage());
                        }
                        else {

                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), true, mCharacters.get(position).getClassType());
                        }

                    }, throwable -> {
                        Log.d("EQUIP_ERROR", throwable.getLocalizedMessage() +", message: "+ throwable.getMessage());
                        mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), false, throwable.getMessage());
                    });

        });

        mTransferRecyclerView.setAdapter(mTransferAdapter);
        mEquipRecyclerView.setAdapter(mEquipAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }



}

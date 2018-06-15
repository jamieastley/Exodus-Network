package com.jastley.warmindfordestiny2.Inventory.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
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
import com.jastley.warmindfordestiny2.Inventory.adapters.EquipItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Inventory.adapters.TransferItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Inventory.interfaces.SuccessListener;
import com.jastley.warmindfordestiny2.Inventory.models.CharacterDatabaseModel;
import com.jastley.warmindfordestiny2.Inventory.models.InventoryItemModel;
import com.jastley.warmindfordestiny2.Definitions;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.api.BungieAPI;
import com.jastley.warmindfordestiny2.api.RetrofitHelper;
import com.jastley.warmindfordestiny2.api.models.EquipItemRequestBody;
import com.jastley.warmindfordestiny2.api.models.TransferItemRequestBody;
import com.squareup.picasso.Picasso;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    TransferItemRequestBody mTransferVaultBody;
    TransferItemRequestBody mTransferCharacterBody;
    EquipItemRequestBody mEquipBody;

    //class variable so equipping can access
    String className;

    private BungieAPI mBungieApi;
    private int mTabIndex;
    private OnFragmentInteractionListener mListener;
    static SuccessListener mSuccessListener;
    private Context mContext;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


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
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mContext = null;
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
        if(selectedItem.getPrimaryStatValue() != null) {
            primaryStatValue.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryStatBackground));
        }
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
        Picasso.get()
                .load(baseURL + selectedItem.getItemIcon())
                .placeholder(R.drawable.missing_icon_d2)
                .into(itemImage);

        if(selectedItem.getIsEquipped()) {
            itemImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_equipped_border));
        }

//        Transfer item row section
        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(),
                                                        selectedItem,
                                                        mTabIndex, //which tab is active
                                                        selectedItem.getVaultCharacterId(), //the characterId of the selected tab
                                                        mCharacters,
                                                        (view, position, holder) -> {

            Log.d("TRANSFER_FROM", "character: "+mCharacters.get(mTabIndex).getClassType());
            Log.d("TRANSFER_TO", "character"+mCharacters.get(position).getCharacterId());

            dismiss();
            mSuccessListener.inProgress("Transferring");

            className = holder.getClassType();
            System.out.println("Destination position: " + position);
            if(mCharacters.get(mTabIndex).getClassType().toLowerCase().equals("vault")) {
                mTransferCharacterBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        false,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(position).getCharacterId() //destination character
                );
                transferToCharacter(mTransferCharacterBody, null, false);
            }

            else if(holder.getClassType().toLowerCase().equals("vault")){ //transfer TO vault selected

                //Send item straight to vault
                mTransferVaultBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        true,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(mTabIndex).getCharacterId() //originating character
//                        holder.getCharacterId()
                        );

                transferToVault(mTransferVaultBody, null, null, false, false);
            }
            else { //Transferring from character to different character

                //1.
                //Send item to vault
                mTransferVaultBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        true,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(mTabIndex).getCharacterId() //originating character
                );

                //2.
                //where to send item after vault transfer
                mTransferCharacterBody = new TransferItemRequestBody(
                        selectedItem.getItemHash(),
                        "1", //TODO: allow stackSize selection if >1 item
                        false,
                        selectedItem.getItemInstanceId(),
                        mCharacters.get(position).getMembershipType(),
                        mCharacters.get(position).getCharacterId() //destination character
                );

                transferToVault(mTransferVaultBody, mTransferCharacterBody, null, false, true);
            }
        });


        //Equip item methods
        mEquipRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mEquipAdapter = new EquipItemRecyclerAdapter(getContext(),
                                                    selectedItem,
                                                    mTabIndex,
                                                    mCharacters,
                                                    (view, position, holder) -> {

            dismiss();
            mSuccessListener.inProgress("Equipping...");

            //where the item is coming from
            String comingFrom = mCharacters.get(mTabIndex).getClassType();

            //destination
            className = holder.getClassType();

            //Set equip body for the row clicked before anything else
            mEquipBody = new EquipItemRequestBody(
                selectedItem.getItemInstanceId(),
                mCharacters.get(position).getCharacterId(),
                mCharacters.get(position).getMembershipType()
            );

            //FIRST REQUEST - transfer to vault
            mTransferVaultBody = new TransferItemRequestBody(
                    selectedItem.getItemHash(),
                    "1", //TODO: allow stackSize selection if >1 item
                    true,
                    selectedItem.getItemInstanceId(),
                    mCharacters.get(position).getMembershipType(),
                    mCharacters.get(mTabIndex).getCharacterId() //originating character
            );

            //where to send item after vault transfer
            mTransferCharacterBody = new TransferItemRequestBody(
                    selectedItem.getItemHash(),
                    "1", //TODO: allow stackSize selection if >1 item
                    false,
                    selectedItem.getItemInstanceId(),
                    mCharacters.get(position).getMembershipType(),
                    mCharacters.get(position).getCharacterId() //destination character
            );

            if(comingFrom.equals(mCharacters.get(position).getClassType())){
                equipItem(mEquipBody, true);
            }

            else if(comingFrom.toLowerCase().equals("vault")){
                transferToCharacter(mTransferCharacterBody, mEquipBody, true);
            }
            else {
                transferToVault(mTransferVaultBody, mTransferCharacterBody, mEquipBody, true, false);
            }
        });

        mTransferRecyclerView.setAdapter(mTransferAdapter);
        mEquipRecyclerView.setAdapter(mEquipAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void transferToVault(TransferItemRequestBody transferBody,
                                 TransferItemRequestBody toCharacterBody,
                                 EquipItemRequestBody equipBody,
                                 boolean isEquipping,
                                 boolean vaultToCharacter){


        Disposable disposable = mBungieApi.transferItem(transferBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")){
                        Log.d("TRANSFER_ERROR: "+response.getErrorCode(), response.getMessage());
                        mSuccessListener.onSuccess(selectedItem.getCurrentPosition(),
                                false,
                                response.getMessage(),
                                true);
                    }
                    else { //item is transferred, now equip to that character
                        if(isEquipping){

                            transferToCharacter(toCharacterBody, equipBody, true);
                        }
                        else if(!isEquipping && vaultToCharacter){
                            transferToCharacter(toCharacterBody, null, false);
                        }
                        else { //just transferring an item, we're done
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), true, className, true);
                            compositeDisposable.dispose();
                        }
                    }

                }, throwable -> {
                    Log.d("TRANSFER_EQUIP_ERROR", throwable.getLocalizedMessage() +", message: "+ throwable.getMessage());
                    mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), false, throwable.getMessage(), true);
                });
        compositeDisposable.add(disposable);
    }

    private void transferToCharacter(TransferItemRequestBody toCharacterBody,
                                      EquipItemRequestBody equipBody,
                                      boolean isEquipping) {

        Disposable disposable = mBungieApi.transferItem(toCharacterBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")){
                        Log.d("TRANSFER_ERROR: "+response.getErrorCode(), response.getMessage());
                        mSuccessListener.onSuccess(selectedItem.getCurrentPosition(),
                                false,
                                response.getMessage(),
                                true);
                    }
                    else { //item is transferred, now equip to that character
                        if(isEquipping){
                            equipItem(equipBody, false);
                        }
                        else { //just transferring an item, we're done
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), true, className, true);
                            compositeDisposable.dispose();
                        }
                    }

                }, throwable -> {
                    Log.d("TRANSFER_EQUIP_ERROR", throwable.getLocalizedMessage() +", message: "+ throwable.getMessage());
                    mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), false, throwable.getMessage(), true);
                });
        compositeDisposable.add(disposable);
    }

    private void equipItem(EquipItemRequestBody body,
                           boolean sameCharacter) {

        Disposable disposable = mBungieApi.equipItem(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.getErrorCode().equals("1")){
                        Log.d("EQUIP_ERROR: "+response.getErrorCode(), response.getMessage());
                        mSuccessListener.onSuccess(selectedItem.getCurrentPosition(),
                                false,
                                response.getErrorCode()+": "+response.getMessage(),
                                false);
                    }
                    else {
                        if(sameCharacter){
                            mSuccessListener.onEquipSameCharacter(selectedItem.getCurrentPosition(), true, className, false);
                        }
                        else{
                            mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), true, className, false);
                        }

                        compositeDisposable.dispose();
                    }

                }, throwable -> {
                    Log.d("EQUIP_ERROR", throwable.getLocalizedMessage() +", message: "+ throwable.getMessage());
                    mSuccessListener.onSuccess(selectedItem.getCurrentPosition(), false, throwable.getMessage(), false);
                });
        compositeDisposable.add(disposable);
    }


}

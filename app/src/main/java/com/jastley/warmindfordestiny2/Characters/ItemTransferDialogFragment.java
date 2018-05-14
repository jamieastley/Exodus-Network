package com.jastley.warmindfordestiny2.Characters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.Characters.adapters.EquipItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.adapters.TransferItemRecyclerAdapter;
import com.jastley.warmindfordestiny2.Characters.interfaces.TransferSelectListener;
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
//    TransferSelectListener mListener;
    InventoryItemModel selectedItem;

    TransferItemRequestBody mTransferBody;
    EquipItemRequestBody mEquipBody;


    private BungieAPI mBungieApi;
    private int mTabIndex;
    private OnFragmentInteractionListener mListener;


    public ItemTransferDialogFragment() {
        super();
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
        mTabIndex = args.getInt("tabIndex");

        return super.onCreateDialog(savedInstanceState);
    }

//    @Override
//    public void dismiss() {
//        onDismiss(DialogInterface::dismiss);
//        super.dismiss();
//    }




    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBungieApi = new RetrofitHelper().getAuthBungieAPI(getContext(), baseURL);

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

        //Get list of users' characters
        CharacterInventoryActivity activity = (CharacterInventoryActivity) getActivity();
        mCharacters = activity.getCharactersList();


//        Transfer item row section
        mTransferRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mTransferAdapter = new TransferItemRecyclerAdapter(getContext(),
                                                        mTabIndex, //which tab is active
                                                        selectedItem.getVaultCharacterId(), //the characterId of the selected tab
                                                        mCharacters,
                                                        (view, position, holder) -> {

            //TODO: display loadingFragment, block UI interaction

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
    //                    holder.getVaultCharacterId()
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
                        //                    holder.getVaultCharacterId()
                        mCharacters.get(position).getCharacterId()
                );
            }


            mBungieApi.transferItem(mTransferBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        System.out.println("Response: " + response.getErrorCode());

//                        dismiss();
//                        onDismiss(this);

                        dismiss();
//                        CharacterInventoryFragment parentFragment = (CharacterInventoryFragment) getParentFragment();
//                        parentFragment.refreshInventory();

                        mListener.onFragmentInteraction("done");

                        if(response.getErrorCode().equals("1")){
                            Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "Item transferred to " + className, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                        }
                    });

        });


        //Equip item methods
        mEquipRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mEquipAdapter = new EquipItemRecyclerAdapter(getContext(), mCharacters, (view, position, holder) -> {

            //Equip item on character
            Toast.makeText(getContext(), holder.getCharacterId(), Toast.LENGTH_SHORT).show();

            String className = holder.getClassType();

            mEquipBody = new EquipItemRequestBody(
                selectedItem.getItemInstanceId(),
                mCharacters.get(position).getCharacterId(),
                mCharacters.get(position).getMembershipType()
            );
            mBungieApi.equipItem(mEquipBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {

                        System.out.println("Response: " + response.getErrorCode());
                        dismiss();

                        CharacterInventoryFragment parentFragment = (CharacterInventoryFragment) getParentFragment();
                        parentFragment.refreshInventory();

                        if(response.getErrorCode().equals("1")){
                            Snackbar.make(getActivity().findViewById(R.id.activity_inventory_main_content), "Item transferred to " + className, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null)
                                    .show();
                        }
                    });

//            "itemId": 1331482397,
//                    "characterId": 2305843009263480022,
//                    "membershipType": 2
//
        });


        mTransferRecyclerView.setAdapter(mTransferAdapter);
        mEquipRecyclerView.setAdapter(mEquipAdapter);


        //Enable/disable UI elements
//        mCharacters.set()

        return super.onCreateView(inflater, container, savedInstanceState);

//        return rootView;
    }




}

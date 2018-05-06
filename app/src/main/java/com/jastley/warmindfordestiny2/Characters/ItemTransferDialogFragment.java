package com.jastley.warmindfordestiny2.Characters;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.R;

public class ItemTransferDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.modal_transfer_image) ImageView firstImage;

    public ItemTransferDialogFragment() {
        super();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_item_transfer_modal, null);

        ButterKnife.bind(this, contentView);

        dialog.setTitle(R.string.transferItem);
        dialog.setContentView(contentView);
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//
//        View rootView = View.inflate(getContext(), R.layout.fragment_item_transfer_modal, null);
//
//        ButterKnife.bind(this, rootView);
//
//        BottomSheetDialogFragment
//    }


}

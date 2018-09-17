package com.jastley.exodusnetwork.Vendors.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class PerksModsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.socket_icon) ImageView socketIcon;
    @BindView(R.id.socket_name) TextView socketName;
    @BindView(R.id.socket_description) TextView socketDesc;

    public PerksModsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public ImageView getSocketIcon() {
        return socketIcon;
    }

    public void setSocketIcon(String url) {

        Picasso.get()
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(socketIcon);
    }

    public TextView getSocketName() {
        return socketName;
    }

    public void setSocketName(String name) {
        this.socketName.setText(name);
    }

    public TextView getSocketDesc() {
        return socketDesc;
    }

    public void setSocketDesc(String desc) {
        this.socketDesc.setText(desc);
    }
}

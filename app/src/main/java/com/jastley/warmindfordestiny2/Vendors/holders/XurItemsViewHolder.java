package com.jastley.warmindfordestiny2.Vendors.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jastley.warmindfordestiny2.R;
import com.squareup.picasso.Picasso;

import static com.jastley.warmindfordestiny2.api.BungieAPI.baseURL;

public class XurItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.xur_item_image) ImageView itemIcon;
    @BindView(R.id.xur_item_type) TextView itemType;
    @BindView(R.id.xur_item_name) TextView itemName;
    @BindView(R.id.xur_item_cost) ImageView itemCostImage;
    @BindView(R.id.xur_item_cost_text) TextView itemCostText;
    @BindView(R.id.xur_total_sales_count) TextView salesCount;

    private String imageUrl;
    private String costIconUrl;

    public XurItemsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public String getItemIcon() {
        return imageUrl;
    }

    public void setItemIcon(String url, Context context) {
        this.imageUrl = url;

        Picasso.with(context)
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(itemIcon);
    }

    public String getItemType() {
        return itemType.getText().toString();
    }

    public void setItemType(String itemType) {
        this.itemType.setText(itemType);
    }

    public String getItemName() {
        return itemName.getText().toString();
    }

    public void setItemName(String itemName) {
        this.itemName.setText(itemName);
    }

    public String getItemCostImage() {
        return costIconUrl;
    }

    public void setItemCostImage(String url, Context context) {
        this.costIconUrl = url;

        if(url != null){
            Picasso.with(context)
                .load(baseURL + url)
                .placeholder(R.drawable.missing_icon_d2)
                .into(this.itemCostImage);
        }
    }

    public String getItemCostText() {
        return itemCostText.getText().toString();
    }

    public void setItemCostText(String cost) {
        itemCostText.setText(cost);
    }

    public String getSalesCount() {
        return salesCount.getText().toString();
    }

    public void setSalesCount(String count) {
//        this.salesCount = count;

        if(count.equals("1")) {

            String countText = "First time!";
            salesCount.setText(countText);

        }
        else {
            String countText = "Sold " + count + " times";
            salesCount.setText(countText);
        }
    }
}

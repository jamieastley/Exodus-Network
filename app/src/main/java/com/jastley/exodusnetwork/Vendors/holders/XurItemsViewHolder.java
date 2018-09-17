package com.jastley.exodusnetwork.Vendors.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.R;
import com.squareup.picasso.Picasso;

import static com.jastley.exodusnetwork.api.BungieAPI.baseURL;

public class XurItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.xur_item_image) ImageView itemIcon;
    @BindView(R.id.xur_item_type) TextView itemType;
    @BindView(R.id.xur_item_name) TextView itemName;
    @BindView(R.id.xur_item_cost) ImageView itemCostImage;
    @BindView(R.id.xur_item_cost_text) TextView itemCostText;
    @BindView(R.id.xur_total_sales_count) TextView salesCount;
    @BindView(R.id.xur_relative_container) RelativeLayout cardContainer;

    private String imageUrl;
    private String costIconUrl;
    private String itemHash;

    private InventoryItemModel testModel;

    private Context mContext;

    public XurItemsViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public InventoryItemModel getTestModel() {
        return testModel;
    }

    public void setTestModel(InventoryItemModel testModel) {
        this.testModel = testModel;
    }

    public String getItemIcon() {
        return imageUrl;
    }

    public void setItemIcon(String url) {
        this.imageUrl = url;

        Picasso.get()
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

    public void setItemCostImage(String url) {
        this.costIconUrl = url;

        if(url != null){
            Picasso.get()
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
            salesCount.setTextColor((mContext).getResources().getColor(R.color.warmindAccent));
            cardContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.xur_first_sale_border));

        }
        else {
            String countText = "Sold " + count + " times";
            salesCount.setText(countText);
        }
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String hash) {
        this.itemHash = hash;
    }
}

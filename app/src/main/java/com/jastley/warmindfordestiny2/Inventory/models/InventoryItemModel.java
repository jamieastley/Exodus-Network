package com.jastley.warmindfordestiny2.Inventory.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.jastley.warmindfordestiny2.Utils.UnsignedHashConverter;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class InventoryItemModel implements Parcelable {

    private String itemHash;
    private String itemInstanceId;
    private String bucketHash;
    //TODO: state (is item locked?)

    //item instance data
    private boolean isEquipped;
    private boolean canEquip;
    private String primaryStatValue;
    private String damageType;

    //Manifest data
    private String classType;
    private String itemName;
    private String itemIcon;
    private String itemTypeDisplayName;
    private Long itemUnsignedHash;
    private String primaryKey;

    //Transferring/equipping
    private int tabIndex;
    private String vaultCharacterId;
    private int currentPosition;

    //Xur
    private String equippingBlock;
    private String costItemIcon;
    private String costsQuantity;
    private String saleHistoryCount;

    //Sorting for RecyclerView
    private int slot;
    private String categoryName;

    public InventoryItemModel() {
    }

    protected InventoryItemModel(Parcel in) {
        itemHash = in.readString();
        itemInstanceId = in.readString();
        bucketHash = in.readString();
        isEquipped = in.readByte() != 0;
        canEquip = in.readByte() != 0;
        primaryStatValue = in.readString();
        classType = in.readString();
        itemName = in.readString();
        itemIcon = in.readString();
    }


    public static final Creator<InventoryItemModel> CREATOR = new Creator<InventoryItemModel>() {
        @Override
        public InventoryItemModel createFromParcel(Parcel in) {
            return new InventoryItemModel(in);
        }

        @Override
        public InventoryItemModel[] newArray(int size) {
            return new InventoryItemModel[size];
        }
    };

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {

//        Long converted = Long.valueOf(itemHash);
//        if(converted > 2147483647L){
//            this.itemHash = UnsignedHashConverter.convert(Long.valueOf(itemHash));
//        }
//        else {
            this.itemHash = itemHash;
//        }
    }

    public String getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(String itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }

    public String getBucketHash() {
        return bucketHash;
    }

    public void setBucketHash(String bucketHash) {
        this.bucketHash = bucketHash;
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public boolean getCanEquip() {
        return canEquip;
    }

    public void setCanEquip(boolean canEquip) {
        this.canEquip = canEquip;
    }

    public String getPrimaryStatValue() {
        return primaryStatValue;
    }

    public void setPrimaryStatValue(String primaryStatValue) {
        this.primaryStatValue = primaryStatValue;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemTypeDisplayName() {
        return itemTypeDisplayName;
    }

    public void setItemTypeDisplayName(String itemTypeDisplayName) {
        this.itemTypeDisplayName = itemTypeDisplayName;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String type) {
        this.damageType = type;
    }

    public String getVaultCharacterId() {
        return vaultCharacterId;
    }

    public void setVaultCharacterId(String vaultCharacterId) {
        this.vaultCharacterId = vaultCharacterId;
    }

    public String getEquippingBlock() {
        return equippingBlock;
    }

    public void setEquippingBlock(String equippingBlock) {
        this.equippingBlock = equippingBlock;
    }

    public String getCostItemIcon() {
        return costItemIcon;
    }

    public void setCostItemIcon(String costItemIcon) {
        this.costItemIcon = costItemIcon;
    }

    public String getCostsQuantity() {
        return costsQuantity;
    }

    public void setCostsQuantity(String costsQuantity) {
        this.costsQuantity = costsQuantity;
    }

    public String getSaleHistoryCount() {
        return saleHistoryCount;
    }

    public void setSaleHistoryCount(String saleHistoryCount) {
        this.saleHistoryCount = saleHistoryCount;
    }

    public Long getItemUnsignedHash() {
        return itemUnsignedHash;
    }

    public void setItemUnsignedHash(Long itemUnsignedHash) {
        this.itemUnsignedHash = itemUnsignedHash;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(itemHash);
        dest.writeString(itemInstanceId);
        dest.writeString(bucketHash);
        dest.writeByte((byte) (isEquipped ? 1 : 0));
        dest.writeByte((byte) (canEquip ? 1 : 0));
        dest.writeString(primaryStatValue);
        dest.writeString(damageType);
        dest.writeString(classType);
        dest.writeString(itemName);
        dest.writeString(itemIcon);
        dest.writeString(itemTypeDisplayName);
        dest.writeInt(tabIndex);
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String key) {

        Long converted = Long.valueOf(key);
        if(converted > 2147483647L){
            this.primaryKey = UnsignedHashConverter.convert(converted);
        }
        else {
            this.primaryKey = key;
        }
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

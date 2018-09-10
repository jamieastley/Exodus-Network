package com.jastley.exodusnetwork.Inventory.models;

import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;
import com.jastley.exodusnetwork.api.models.Response_GetCharacterInventory;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

import java.util.List;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class InventoryItemModel {

    private String itemHash;
    private String itemInstanceId;
    private String bucketHash;
    //TODO: state (is item locked?)

    //item instance data
    private boolean isEquipped;
    private boolean canEquip;
    private String primaryStatValue;
    private String damageType;
    private int quantity;

    //Manifest data
    private String classType;
    private String itemName;
    private String itemIcon;
    private String description;
    private String itemTypeDisplayName;
    private Long itemUnsignedHash;
    private String primaryKey;
    private int ammoType;

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
    private boolean isLocked;
    private boolean isMasterwork;
    private boolean isTracked;

    //    public InventoryItemModel() {
//    }
//
//    protected InventoryItemModel(Parcel in) {
//        itemHash = in.readString();
//        itemInstanceId = in.readString();
//        bucketHash = in.readString();
//        isEquipped = in.readByte() != 0;
//        canEquip = in.readByte() != 0;
//        primaryStatValue = in.readString();
//        classType = in.readString();
//        itemName = in.readString();
//        itemIcon = in.readString();
//    }
//
//
//    public static final Creator<InventoryItemModel> CREATOR = new Creator<InventoryItemModel>() {
//        @Override
//        public InventoryItemModel createFromParcel(Parcel in) {
//            return new InventoryItemModel(in);
//        }
//
//        @Override
//        public InventoryItemModel[] newArray(int size) {
//            return new InventoryItemModel[size];
//        }
//    };
//
    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
            this.itemHash = itemHash;
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
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//        dest.writeString(itemHash);
//        dest.writeString(itemInstanceId);
//        dest.writeString(bucketHash);
//        dest.writeByte((byte) (isEquipped ? 1 : 0));
//        dest.writeByte((byte) (canEquip ? 1 : 0));
//        dest.writeString(primaryStatValue);
//        dest.writeString(damageType);
//        dest.writeString(classType);
//        dest.writeString(itemName);
//        dest.writeString(itemIcon);
//        dest.writeString(itemTypeDisplayName);
//        dest.writeInt(tabIndex);
//    }
//
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

//    public int getSlot() {
//        return slot;
//    }
//
//    public void setSlot(int slot) {
//        this.slot = slot;
//    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
////        return super.equals(obj);
//        if(this == obj) return true;
//        if(obj == null || getClass() != obj.getClass()) return false;
//
//        InventoryItemModel model = (InventoryItemModel) obj;
//
//        if(!itemName.equals(model.itemName)) return false;
//        return itemName != null ? itemName.equals(model.getItemName()) : model.getItemName() == null;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

//    private InventoryItemJsonData inventoryItem;
//    private InventoryItemJsonData.DisplayProperties displayProperties;
//    private Response_GetCharacterInventory.Items itemData;
//    private Response_GetCharacterInventory.InstanceData instanceData;

    //LiveData
    private String message;
    private Throwable throwable;
    private List<InventoryItemModel> itemModelList;
//    private int slot;

    public InventoryItemModel() {
    }

    public InventoryItemModel(String message) {
        this.message = message;
    }

    public InventoryItemModel(Throwable throwable) {
        this.throwable = throwable;
    }

    public InventoryItemModel(List<InventoryItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

//    public InventoryItemJsonData getInventoryItem() {
//        return inventoryItem;
//    }
//
//    public void setInventoryItem(InventoryItemJsonData inventoryItem) {
//        this.inventoryItem = inventoryItem;
//    }
//
//    public InventoryItemJsonData.DisplayProperties getDisplayProperties() {
//        return displayProperties;
//    }
//
//    public void setDisplayProperties(InventoryItemJsonData.DisplayProperties displayProperties) {
//        this.displayProperties = displayProperties;
//    }
//
//    public Response_GetCharacterInventory.Items getItemData() {
//        return itemData;
//    }
//
//    public void setItemData(Response_GetCharacterInventory.Items itemData) {
//        this.itemData = itemData;
//    }
//
//    public Response_GetCharacterInventory.InstanceData getInstanceData() {
//        return instanceData;
//    }
//
//    public void setInstanceData(Response_GetCharacterInventory.InstanceData instanceData) {
//        this.instanceData = instanceData;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public List<InventoryItemModel> getItemModelList() {
        return itemModelList;
    }

    public void setItemModelList(List<InventoryItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setIsLocked(int flag) {
        if(flag != 0) {
            this.isLocked = true;
        }
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public boolean isMasterwork() {
        return isMasterwork;
    }

    public void setMasterwork(int flag) {
        if(flag != 0) {
            isMasterwork = true;
        }
    }

    public boolean isTracked() {
        return isTracked;
    }

    public void setTracked(int flag) {
        if(flag != 0) {
            isTracked = true;
        }
    }

    public int getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(int ammoType) {
        this.ammoType = ammoType;
    }
}

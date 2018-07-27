package com.jastley.exodusnetwork.Milestones.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InventoryDataModel {

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("itemDisplayName")
    private String itemDisplayName;
    @Expose
    @SerializedName("Inventory")
    private Inventory inventory;
    @Expose
    @SerializedName("nonTransferrable")
    private boolean isNonTransferrable;
    @Expose
    @SerializedName("itemTypeDisplayName")
    private String itemTypeDisplayName;
    @Expose
    @SerializedName("itemTypeAndTierDisplayName")
    private String itemTypeAndTierDisplayName;
    @Expose
    @SerializedName("itemType")
    private String itemType;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("classType")
    private String classType;
    @Expose
    @SerializedName("equippable")
    private boolean isEquippable;


    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public String getItemTypeDisplayName() {
        return itemTypeDisplayName;
    }

    public String getItemTypeAndTierDisplayName() {
        return itemTypeAndTierDisplayName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isNonTransferrable() {
        return isNonTransferrable;
    }

    public String getItemType() {
        return itemType;
    }

    public String getHash() {
        return hash;
    }

    public String getClassType() {
        return classType;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public static class DisplayProperties {
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("icon")
        private String icon;
        @Expose
        @SerializedName("hasIcon")
        private boolean hasIcon;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }
    }

    public static class Inventory {
        @Expose
        @SerializedName("maxStackSize")
        private String maxStackSize;
        @Expose
        @SerializedName("bucketTypeHash")
        private String bucketTypeHash;
        @Expose
        @SerializedName("recoveryBucketTypeHash")
        private String recoveryBucketTypeHash;
        @Expose
        @SerializedName("tierTypeHash")
        private String tierTypeHash;
        @Expose
        @SerializedName("isInstanceItem")
        private boolean isInstanceItem;
        @Expose
        @SerializedName("nonTransferrableOriginal")
        private boolean isNonTransferrableOriginal;
        @Expose
        @SerializedName("tierTypeName")
        private String tierTypeName;
        @Expose
        @SerializedName("tierType")
        private String tierType;

        public String getMaxStackSize() {
            return maxStackSize;
        }

        public String getBucketTypeHash() {
            return bucketTypeHash;
        }

        public String getRecoveryBucketTypeHash() {
            return recoveryBucketTypeHash;
        }

        public String getTierTypeHash() {
            return tierTypeHash;
        }

        public boolean isInstanceItem() {
            return isInstanceItem;
        }

        public boolean isNonTransferrableOriginal() {
            return isNonTransferrableOriginal;
        }

        public String getTierTypeName() {
            return tierTypeName;
        }

        public String getTierType() {
            return tierType;
        }
    }
}

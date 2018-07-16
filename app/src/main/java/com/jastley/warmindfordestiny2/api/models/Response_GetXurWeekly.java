package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jastley.warmindfordestiny2.Inventory.models.InventoryItemModel;

import java.util.List;

public class Response_GetXurWeekly {

    private List<InventoryItemModel> itemList;
    private String errorMessage;

    //Response successful
    public Response_GetXurWeekly(List<InventoryItemModel> itemList) {
        this.itemList = itemList;
        this.errorMessage = null;
    }

    //API error
    public Response_GetXurWeekly(String error) {
        this.itemList = null;
        this.errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<InventoryItemModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<InventoryItemModel> itemList) {
        this.itemList = itemList;
    }

    @Expose
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {

        @Expose
        @SerializedName("status")
        private String status;

        @Expose
        @SerializedName("message")
        private String message;

        @Expose
        @SerializedName("data")
        private Data data;

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        @Expose
        @SerializedName("location")
        private Location location;

        @Expose
        @SerializedName("season")
        private String season;

        @Expose
        @SerializedName("week")
        private String week;

        @Expose
        @SerializedName("items")
        private List<Items> items;

        public Location getLocation() {
            return location;
        }

        public String getSeason() {
            return season;
        }

        public String getWeek() {
            return week;
        }

        public List<Items> getItems() {
            return items;
        }
    }

    public static class Location {

        @Expose
        @SerializedName("id")
        private String id;

        @Expose
        @SerializedName("gps")
        private Gps gps;

        @Expose
        @SerializedName("world")
        private String world;
        @Expose
        @SerializedName("region")
        private String region;

        @Expose
        @SerializedName("description")
        private String description;

        public String getId() {
            return id;
        }

        public Gps getGps() {
            return gps;
        }

        public String getWorld() {
            return world;
        }

        public String getRegion() {
            return region;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Gps {

        @Expose
        @SerializedName("x")
        private String xCoords;

        @Expose
        @SerializedName("y")
        private String yCoords;

        public String getxCoords() {
            return xCoords;
        }

        public String getyCoords() {
            return yCoords;
        }
    }

    public static class Items {

        @Expose
        @SerializedName("sales")
        private String salesCount;

        @Expose
        @SerializedName("hash")
        private String hash;

        @Expose
        @SerializedName("displayProperties")
        private DisplayProperties displayProperties;

        @Expose
        @SerializedName("classType")
        private String classType;

        @Expose
        @SerializedName("itemTypeDisplayName")
        private String itemTypeDisplayName;

        @Expose
        @SerializedName("equippable")
        private boolean equippable;

        @Expose
        @SerializedName("equippingBlock")
        private EquippingBlock equippingBlock;

        @Expose
        @SerializedName("cost")
        private Cost cost;


        public String getSalesCount() {
            return salesCount;
        }

        public String getHash() {
            return hash;
        }

        public DisplayProperties getDisplayProperties() {
            return displayProperties;
        }

        public String getClassType() {
            return classType;
        }

        public String getItemTypeDisplayName() {
            return itemTypeDisplayName;
        }

        public boolean isEquippable() {
            return equippable;
        }

        public EquippingBlock getEquippingBlock() {
            return equippingBlock;
        }

        public Cost getCost() {
            return cost;
        }

    }

    public static class Cost {

        @Expose
        @SerializedName("quantity")
        private String quantity;

        @Expose
        @SerializedName("icon")
        private String icon;

        public String getQuantity() {
            return quantity;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class EquippingBlock {

        @Expose
        @SerializedName("uniqueLabel")
        private String uniqueLabel;

        @Expose
        @SerializedName("uniqueLabelHash")
        private String uniqueLabelHash;

        @Expose
        @SerializedName("equipmentSlotTypeHash")
        private String equipmentSlotTypeHash;

        @Expose
        @SerializedName("attributes")
        private String attributes;

        @Expose
        @SerializedName("equippingSoundHash")
        private String equippingSoundHash;

        @Expose
        @SerializedName("hornSoundHash")
        private String hornSoundHash;

        @Expose
        @SerializedName("displayStrings")
        private List<String> displayStrings;

        public String getUniqueLabel() {
            return uniqueLabel;
        }

        public String getUniqueuLabelHash() {
            return uniqueLabelHash;
        }

        public String getEquipmentSlotTypeHash() {
            return equipmentSlotTypeHash;
        }

        public String getAttributes() {
            return attributes;
        }

        public String getEquippingSoundHash() {
            return equippingSoundHash;
        }

        public String getHornSoundHash() {
            return hornSoundHash;
        }

        public List<String> getDisplayStrings() {
            return displayStrings;
        }
    }

    public static class DisplayProperties {

        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("icon")
        private String icon;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("hasIcon")
        private boolean hasIcon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }
    }

}

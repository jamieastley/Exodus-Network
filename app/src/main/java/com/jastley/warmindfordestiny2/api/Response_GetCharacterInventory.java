package com.jastley.warmindfordestiny2.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by jamie on 21/3/18.
 */

public class Response_GetCharacterInventory {


    @Expose
    @SerializedName("MessageData")
    private MessageData MessageData;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("ErrorStatus")
    private String ErrorStatus;
    @Expose
    @SerializedName("ThrottleSeconds")
    private int ThrottleSeconds;
    @Expose
    @SerializedName("ErrorCode")
    private int ErrorCode;
    @Expose
    @SerializedName("Response")
    private Response Response;

    public MessageData getMessageData() {
        return MessageData;
    }

    public String getMessage() {
        return Message;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public int getThrottleSeconds() {
        return ThrottleSeconds;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public Response getResponse() {
        return Response;
    }

    public static class MessageData {
    }

    public static class Response {
        @Expose
        @SerializedName("itemComponents")
        private ItemComponents itemComponents;
        @Expose
        @SerializedName("inventory")
        private Inventory inventory;
        @Expose
        @SerializedName("profileInventory")
        private ProfileInventory profileInventory;

        public ItemComponents getItemComponents() {
            return itemComponents;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public ProfileInventory getProfileInventory() {
            return profileInventory;
        }
    }

    public static class ItemComponents {
        @Expose
        @SerializedName("instances")
        private Instances instances;

        public Instances getInstances() {
            return instances;
        }
    }

    public static class Instances {
        @Expose
        @SerializedName("data")
        private Map<String, InstanceData>  data;
//        private InstanceData instanceData;

        public Map<String, InstanceData> getInstanceData() {
            return data;
        }

//        public InstanceData getInstanceData() {
//            return instanceData;
//        }
    }

    public static class InstanceData {
        @Expose
        @SerializedName("damageType")
        private int damageType;
        @Expose
        @SerializedName("damageTypeHash")
        private Double damageTypeHash;
        @Expose
        @SerializedName("itemLevel")
        private int itemLevel;
        @Expose
        @SerializedName("quality")
        private int quality;
        @Expose
        @SerializedName("getIsEquipped")
        private boolean isEquipped;
        @Expose
        @SerializedName("canEquip")
        private boolean canEquip;
        @Expose
        @SerializedName("equipRequiredLevel")
        private int equipRequiredLevel;
        @Expose
        @SerializedName("primaryStat")
        private PrimaryStat primaryStat;

        public PrimaryStat getPrimaryStat() {
            return primaryStat;
        }

        public int getDamageType() {
            return damageType;
        }

        public Double getDamageTypeHash() {
            return damageTypeHash;
        }

        public int getItemLevel() {
            return itemLevel;
        }

        public int getQuality() {
            return quality;
        }

        public boolean getIsEquipped() {
            return isEquipped;
        }

        public boolean getCanEquip() {
            return canEquip;
        }

        public int getEquipRequiredLevel() {
            return equipRequiredLevel;
        }
    }

    public static class PrimaryStat {
        @Expose
        @SerializedName("statHash")
        private Double statHash;
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("maximumValue")
        private int maximumValue;

        public Double getStatHash() {
            return statHash;
        }

        public String getValue() {
            return value;
        }

        public int getMaximumValue() {
            return maximumValue;
        }
    }

    public static class ProfileInventory {
        @Expose
        @SerializedName("privacy")
        private int privacy;
        @Expose
        @SerializedName("data")
        private Data data;

        public int getPrivacy() {
            return privacy;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Inventory {
        @Expose
        @SerializedName("privacy")
        private int privacy;
        @Expose
        @SerializedName("data")
        private Data data;

        public int getPrivacy() {
            return privacy;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        @Expose
        @SerializedName("items")
        private List<Items> items;

        public List<Items> getItems() {
            return items;
        }
    }

    public static class Items {
        @Expose
        @SerializedName("dismantlePermission")
        private int dismantlePermission;
        @Expose
        @SerializedName("state")
        private int state;
        @Expose
        @SerializedName("lockable")
        private boolean lockable;
        @Expose
        @SerializedName("transferStatus")
        private int transferStatus;
        @Expose
        @SerializedName("bucketHash")
        private String bucketHash;
        @Expose
        @SerializedName("location")
        private int location;
        @Expose
        @SerializedName("bindStatus")
        private int bindStatus;
        @Expose
        @SerializedName("quantity")
        private int quantity;
        @Expose
        @SerializedName("itemInstanceId")
        private String itemInstanceId;
        @Expose
        @SerializedName("itemHash")
        private String itemHash;

        public int getDismantlePermission() {
            return dismantlePermission;
        }

        public int getState() {
            return state;
        }

        public boolean getLockable() {
            return lockable;
        }

        public int getTransferStatus() {
            return transferStatus;
        }

        public String getBucketHash() {
            return bucketHash;
        }

        public int getLocation() {
            return location;
        }

        public int getBindStatus() {
            return bindStatus;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getItemInstanceId() {
            return itemInstanceId;
        }

        public String getItemHash() {
            return itemHash;
        }
    }
}

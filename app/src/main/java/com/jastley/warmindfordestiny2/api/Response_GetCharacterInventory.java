package com.jastley.warmindfordestiny2.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

        public ItemComponents getItemComponents() {
            return itemComponents;
        }

        public Inventory getInventory() {
            return inventory;
        }
    }

    public static class ItemComponents {
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
        private int bucketHash;
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
        private int itemHash;

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

        public int getBucketHash() {
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

        public int getItemHash() {
            return itemHash;
        }
    }
}

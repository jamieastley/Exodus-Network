package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Response_GetVendor {

    @Expose
    @SerializedName("Response")
    private Response response;

    @Expose
    @SerializedName("ErrorCode")
    private String errorCode;

    @Expose
    @SerializedName("Message")
    private String message;

    public Response getResponse() {
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static class Response {
        @Expose
        @SerializedName("vendor")
        private Vendor vendor;

        //categories - not required

        @Expose
        @SerializedName("sales")
        private Sales sales;


        public Vendor getVendor() {
            return vendor;
        }

        //categories - not required

        public Sales getSales() {
            return sales;
        }

    }



    public static class Vendor {

        @Expose
        @SerializedName("data")
        private VendorData data;

        public VendorData getData() {
            return data;
        }
    }

    public static class VendorData {
        @Expose
        @SerializedName("vendorHash")
        private String vendorHash;

        @Expose
        @SerializedName("nextRefreshDate")
        private String nextRefreshDate;

        @Expose
        @SerializedName("enabled")
        private boolean enabled;

        @Expose
        @SerializedName("canPurchase")
        private boolean canPurchase;

        @Expose
        @SerializedName("vendorLocationIndex")
        private String vendorLocationIndex;

        public String getVendorHash() {
            return vendorHash;
        }

        public String getNextRefreshDate() {
            return nextRefreshDate;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isCanPurchase() {
            return canPurchase;
        }

        public String getVendorLocationIndex() {
            return vendorLocationIndex;
        }
    }

    public static class Sales {

        @Expose
        @SerializedName("data")
        private Map<String, SalesData> salesData;

        public Map<String, SalesData> getSalesData() {
            return salesData;
        }
    }

    public static class SalesData {

        @Expose
        @SerializedName("vendorItemIndex")
        private String vendorItemIndex;

        @Expose
        @SerializedName("itemHash")
        private String itemHash;

        @Expose
        @SerializedName("quantity")
        private String quantity;

        @Expose
        @SerializedName("saleStatus")
        private String saleStatus;

        @Expose
        @SerializedName("costs")
        private List<Costs> costsList;

        @Expose
        @SerializedName("requiredUnlocks")
        private List<RequiredUnlocks> requiredUnlocksList;

        @Expose
        @SerializedName("unlockStatuses")
        private List<UnlockStatuses> unlockStatusesList;

        @Expose
        @SerializedName("failureIndexes")
        private List<FailureIndexes> failureIndexesList;

        @Expose
        @SerializedName("augments")
        private String augments;

        public String getVendorItemIndex() {
            return vendorItemIndex;
        }

        public String getItemHash() {
            return itemHash;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getSaleStatus() {
            return saleStatus;
        }

        public List<Costs> getCostsList() {
            return costsList;
        }

        public List<RequiredUnlocks> getRequiredUnlocksList() {
            return requiredUnlocksList;
        }

        public List<UnlockStatuses> getUnlockStatusesList() {
            return unlockStatusesList;
        }

        public List<FailureIndexes> getFailureIndexesList() {
            return failureIndexesList;
        }

        public String getAugments() {
            return augments;
        }
    }

    public static class Costs {
        @Expose
        @SerializedName("itemHash")
        private String itemHash;

        @Expose
        @SerializedName("quantity")
        private String quantity;

        public String getItemHash() {
            return itemHash;
        }

        public String getQuantity() {
            return quantity;
        }
    }

    public static class RequiredUnlocks {

    }

    public static class UnlockStatuses {
        @Expose
        @SerializedName("unlockHash")
        private String unlockHash;

        @Expose
        @SerializedName("isSet")
        private String isSet;

        public String getUnlockHash() {
            return unlockHash;
        }

        public String getIsSet() {
            return isSet;
        }
    }

    public static class FailureIndexes {

    }
}

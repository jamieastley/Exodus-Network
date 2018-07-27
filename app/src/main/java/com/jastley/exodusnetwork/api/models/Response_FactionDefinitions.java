package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_FactionDefinitions {

    public static class FactionObject {
        @Expose
        @SerializedName("displayProperties")
        private DisplayProperties displayProperties;

        @Expose
        @SerializedName("vendors")
        private List<Vendor> vendors = null;

        @Expose
        @SerializedName("rewardItemHash")
        private String rewardItemHash;

        public DisplayProperties getDisplayProperties() {
            return displayProperties;
        }

        public List<Vendor> getVendors() {
            return vendors;
        }

        public String getRewardItemHash() {
            return rewardItemHash;
        }
    }

    public static class Vendor {
        @Expose
        @SerializedName("vendorHash")
        private String vendorHash;

        @Expose
        @SerializedName("destinationHash")
        private String destinationHash;

        @Expose
        @SerializedName("backgroundImagePath")
        private String backgroundImagePath;

        public String getVendorHash() {
            return vendorHash;
        }

        public String getDestinationHash() {
            return destinationHash;
        }

        public String getBackgroundImagePath() {
            return backgroundImagePath;
        }
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

        public boolean getHasIcon() {
            return hasIcon;
        }
    }

}

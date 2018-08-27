package com.jastley.exodusnetwork.database.jsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatDefinitionData {

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("aggregationType")
    private int aggregationType;
    @Expose
    @SerializedName("hasComputedBlock")
    private boolean hasComputedBlock;
    @Expose
    @SerializedName("statCategory")
    private int statCategory;
    @Expose
    @SerializedName("interpolate")
    private boolean doesInterpolate;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("index")
    private int index;
    @Expose
    @SerializedName("redacted")
    private boolean isRedacted;
    @Expose
    @SerializedName("blacklisted")
    private boolean isBlacklisted;

    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public int getAggregationType() {
        return aggregationType;
    }

    public boolean isHasComputedBlock() {
        return hasComputedBlock;
    }

    public int getStatCategory() {
        return statCategory;
    }

    public boolean isDoesInterpolate() {
        return doesInterpolate;
    }

    public String getHash() {
        return hash;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRedacted() {
        return isRedacted;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public static class DisplayProperties {
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("hasIcon")
        private boolean hasIcon;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public boolean hasIcon() {
            return hasIcon;
        }
    }
}

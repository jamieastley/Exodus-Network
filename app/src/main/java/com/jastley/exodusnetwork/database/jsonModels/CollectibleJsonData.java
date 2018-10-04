package com.jastley.exodusnetwork.database.jsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectibleJsonData {

    private int profileState;
    private int listPosition;
    private boolean isAcquired;

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("scope")
    private int scope;
    @Expose
    @SerializedName("sourceString")
    private String sourceString;
    @Expose
    @SerializedName("sourceHash")
    private String sourceHash;
    @Expose
    @SerializedName("itemHash")
    private String itemHash;
    @Expose
    @SerializedName("acquisitionInfo")
    private AcquisitionInfo acquisitionInfo;
    @Expose
    @SerializedName("stateInfo")
    private StateInfo stateInfo;
    @Expose
    @SerializedName("presentationInfo")
    private PresentationInfo presentationInfo;
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

    public int getScope() {
        return scope;
    }

    public String getSourceString() {
        return sourceString;
    }

    public String getSourceHash() {
        return sourceHash;
    }

    public String getItemHash() {
        return itemHash;
    }

    public AcquisitionInfo getAcquisitionInfo() {
        return acquisitionInfo;
    }

    public StateInfo getStateInfo() {
        return stateInfo;
    }

    public PresentationInfo getPresentationInfo() {
        return presentationInfo;
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

    public int getProfileState() {
        return profileState;
    }

    public void setProfileState(int profileState) {
        this.profileState = profileState;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public boolean isAcquired() {
        return isAcquired;
    }

    public void setAcquired(boolean acquired) {
        isAcquired = acquired;
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

    public static class AcquisitionInfo {

    }

    public static class StateInfo {

    }

    public static class PresentationInfo {

    }
}

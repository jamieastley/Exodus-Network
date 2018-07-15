package com.jastley.warmindfordestiny2.Vendors.models;

public class XurVendorModel {

    private String worldName;
    private String regionText;
    private String locationIndex;

    public XurVendorModel(String worldName, String regionText, String locationIndex) {
        this.worldName = worldName;
        this.regionText = regionText;
        this.locationIndex = locationIndex;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getRegionText() {
        return regionText;
    }

    public void setRegionText(String regionText) {
        this.regionText = regionText;
    }

    public String getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(String locationIndex) {
        this.locationIndex = locationIndex;
    }
}

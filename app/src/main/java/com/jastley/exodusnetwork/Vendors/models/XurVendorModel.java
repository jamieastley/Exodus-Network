package com.jastley.exodusnetwork.Vendors.models;

public class XurVendorModel {

    private String worldName;
    private String regionText;
    private String locationIndex;
    private String locationBanner;

    private String errorMessage;

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

    public String getLocationBanner() {
        return locationBanner;
    }

    public void setLocationBanner(String locationBanner) {
        this.locationBanner = locationBanner;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

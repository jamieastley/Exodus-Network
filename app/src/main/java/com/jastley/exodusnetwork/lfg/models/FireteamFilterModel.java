package com.jastley.exodusnetwork.lfg.models;

public class FireteamFilterModel {

    private String platform;
    private String activityType;
    private String dateRange;
    private String slotFilter;
    private String page;

    public FireteamFilterModel(String platform, String activityType, String dateRange, String slotFilter, String page) {
        this.platform = platform;
        this.activityType = activityType;
        this.dateRange = dateRange;
        this.slotFilter = slotFilter;
        this.page = page;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getSlotFilter() {
        return slotFilter;
    }

    public void setSlotFilter(String slotFilter) {
        this.slotFilter = slotFilter;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}

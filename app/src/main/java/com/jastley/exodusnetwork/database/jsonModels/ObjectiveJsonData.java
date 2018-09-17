package com.jastley.exodusnetwork.database.jsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectiveJsonData {

    private List<ObjectiveJsonData> objectiveJsonDataList;

    public ObjectiveJsonData(List<ObjectiveJsonData> list) {
        this.objectiveJsonDataList = list;
    }

    public List<ObjectiveJsonData> getObjectiveJsonDataList() {
        return objectiveJsonDataList;
    }

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("unlockValueHash")
    private String unlockValueHash;
    @Expose
    @SerializedName("completionValue")
    private int completionValue;
    @Expose
    @SerializedName("scope")
    private int scope;
    @Expose
    @SerializedName("locationHash")
    private String locationHash;
    @Expose
    @SerializedName("allowNegativeValue")
    private boolean allowNegativeValue;
    @Expose
    @SerializedName("allowValueChangeWhenCompleted")
    private boolean allowValueChangeWhenCompleted;
    @Expose
    @SerializedName("isCountingDownward")
    private boolean isCountingDownward;
    @Expose
    @SerializedName("valueStyle")
    private String valueStyle;
    @Expose
    @SerializedName("progressDescription")
    private String progressDescription;
    @Expose
    @SerializedName("perks")
    private Perks perks;
    @Expose
    @SerializedName("stats")
    private Stats stats;
    @Expose
    @SerializedName("minimumVisibilityThreshold")
    private String minimumVisibilityThreshold;
    @Expose
    @SerializedName("showValueOnComplete")
    private boolean showValueOnCOmplete;
    @Expose
    @SerializedName("isDisplayOnlyObjective")
    private boolean isDisplayOnlyObjective;
    @Expose
    @SerializedName("completedValueStyle")
    private String completedValueStyle;
    @Expose
    @SerializedName("inProgressValueStyle")
    private String inProgressValueStyle;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("index")
    private String index;
    @Expose
    @SerializedName("redacted")
    private boolean redacted;
    @Expose
    @SerializedName("blacklisted")
    private boolean blacklisted;

    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public String getUnlockValueHash() {
        return unlockValueHash;
    }

    public int getCompletionValue() {
        return completionValue;
    }

    public int getScope() {
        return scope;
    }

    public String getLocationHash() {
        return locationHash;
    }

    public boolean isAllowNegativeValue() {
        return allowNegativeValue;
    }

    public boolean isAllowValueChangeWhenCompleted() {
        return allowValueChangeWhenCompleted;
    }

    public boolean isCountingDownward() {
        return isCountingDownward;
    }

    public String getValueStyle() {
        return valueStyle;
    }

    public String getProgressDescription() {
        return progressDescription;
    }

    public Perks getPerks() {
        return perks;
    }

    public Stats getStats() {
        return stats;
    }

    public String getMinimumVisibilityThreshold() {
        return minimumVisibilityThreshold;
    }

    public boolean isShowValueOnCOmplete() {
        return showValueOnCOmplete;
    }

    public boolean isDisplayOnlyObjective() {
        return isDisplayOnlyObjective;
    }

    public String getCompletedValueStyle() {
        return completedValueStyle;
    }

    public String getInProgressValueStyle() {
        return inProgressValueStyle;
    }

    public String getHash() {
        return hash;
    }

    public String getIndex() {
        return index;
    }

    public boolean isRedacted() {
        return redacted;
    }

    public boolean isBlacklisted() {
        return blacklisted;
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

        public boolean isHasIcon() {
            return hasIcon;
        }
    }

    public static class Perks {
        @Expose
        @SerializedName("perkHash")
        private String perkHash;
        @Expose
        @SerializedName("style")
        private String style;

        public String getPerkHash() {
            return perkHash;
        }

        public String getStyle() {
            return style;
        }
    }

    public static class Stats {
        @Expose
        @SerializedName("style")
        private String style;
    }
}

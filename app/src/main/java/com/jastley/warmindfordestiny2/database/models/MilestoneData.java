package com.jastley.warmindfordestiny2.database.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class MilestoneData {

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("milestoneType")
    private String milestoneType;
    @Expose
    @SerializedName("recruitable")
    private boolean isRecruitable;
    @Expose
    @SerializedName("friendlyName")
    private String friendlyName;
    @Expose
    @SerializedName("showInExplorer")
    private boolean showInExplorer;
    @Expose
    @SerializedName("explorePrioritizesActivityImage")
    private boolean explorePrioritizesActivityImage;
    @Expose
    @SerializedName("hasPredictableDates")
    private boolean hasPredictableDates;
    @Expose
    @SerializedName("quests")
    private Map<String, Quests> questsData;

    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public boolean isRecruitable() {
        return isRecruitable;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public boolean isShowInExplorer() {
        return showInExplorer;
    }

    public boolean isExplorePrioritizesActivityImage() {
        return explorePrioritizesActivityImage;
    }

    public boolean isHasPredictableDates() {
        return hasPredictableDates;
    }

    public Map<String, Quests> getQuestsData() {
        return questsData;
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

    public static class Quests {
        @Expose
        @SerializedName("questItemHash")
        private String questItemHash;
        @Expose
        @SerializedName("displayProperties")
        private DisplayProperties displayProperties;
        @Expose
        @SerializedName("questRewards")
        private QuestRewards questRewards;
        @Expose
        @SerializedName("activities")
        private Map<String, Activities> activities;
        @Expose
        @SerializedName("trackingUnlockValueHash")
        private String trackingUnlockValueHash;

        public String getQuestItemHash() {
            return questItemHash;
        }

        public DisplayProperties getDisplayProperties() {
            return displayProperties;
        }

        public QuestRewards getQuestRewards() {
            return questRewards;
        }

        public Map<String, Activities> getActivities() {
            return activities;
        }

        public String getTrackingUnlockValueHash() {
            return trackingUnlockValueHash;
        }
    }

    public static class Activities {
        @Expose
        @SerializedName("conceptualActivityHash")
        private String conceptualActivityHash;
        @Expose
        @SerializedName("variants")
        private Map<String, Variants> variants;

        public String getConceptualActivityHash() {
            return conceptualActivityHash;
        }

        public Map<String, Variants> getVariants() {
            return variants;
        }
    }

    public static class Variants {
        @Expose
        @SerializedName("activityHash")
        private String activityHash;
        @Expose
        @SerializedName("order")
        private String order;

        public String getActivityHash() {
            return activityHash;
        }

        public String getOrder() {
            return order;
        }
    }

    public static class QuestRewards {
        @Expose
        @SerializedName("items")
        private List<RewardItems> rewardItemsList;

        public List<RewardItems> getRewardItemsList() {
            return rewardItemsList;
        }

    }

    public static class RewardItems {
        @Expose
        @SerializedName("vendorHash")
        private String vendorHash;
        @Expose
        @SerializedName("vendorItemIndex")
        private String vendorItemIndex;
        @Expose
        @SerializedName("itemHash")
        private String itemHash;
        @Expose
        @SerializedName("quantity")
        private String quantity;

        public String getVendorHash() {
            return vendorHash;
        }

        public String getVendorItemIndex() {
            return vendorItemIndex;
        }

        public String getItemHash() {
            return itemHash;
        }

        public String getQuantity() {
            return quantity;
        }
    }
}

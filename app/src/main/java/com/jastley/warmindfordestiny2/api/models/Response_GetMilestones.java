package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_GetMilestones {

    /** Milestone top-level **/

    @Expose
    @SerializedName("milestoneHash")
    private String milestoneHash;
    @Expose
    @SerializedName("availableQuests")
    private List<AvailableQuests> availableQuests;

    public String getMilestoneHash() {
        return milestoneHash;
    }

    public List<AvailableQuests> getAvailableQuests() {
        return availableQuests;
    }



    public static class AvailableQuests {

        @Expose
        @SerializedName("questItemHash")
        private String questItemHash;
        @Expose
        @SerializedName("activity")
        private Activity activity;
        @Expose
        @SerializedName("challenges")
        private List<Challenges> challenges;

        public String getQuestItemHash() {
            return questItemHash;
        }

        public Activity getActivity() {
            return activity;
        }

        public List<Challenges> getChallenges() {
            return challenges;
        }
    }

    public static class Activity {

        @Expose
        @SerializedName("activityHash")
        private String activityHash;
        @Expose
        @SerializedName("variants")
        private List<Variants> variants;
        @Expose
        @SerializedName("activityModeHash")
        private String activityModeHash;
        @Expose
        @SerializedName("activityModeType")
        private String activityModeType;

        public String getActivityHash() {
            return activityHash;
        }

        public List<Variants> getVariants() {
            return variants;
        }

        public String getActivityModeHash() {
            return activityModeHash;
        }

        public String getActivityModeType() {
            return activityModeType;
        }
    }

    public static class Variants {
        @Expose
        @SerializedName("activityHash")
        private String activityHash;
        @Expose
        @SerializedName("activityModeHash")
        private String activityModeHash;
        @Expose
        @SerializedName("activityModeType")
        private String activityModeType;

        public String getActivityHash() {
            return activityHash;
        }

        public String getActivityModeHash() {
            return activityModeHash;
        }

        public String getActivityModeType() {
            return activityModeType;
        }
    }

    public static class Challenges {
        @Expose
        @SerializedName("objectiveHash")
        private String objectiveHash;
        @Expose
        @SerializedName("activityHash")
        private String activityHash;

        public String getObjectiveHash() {
            return objectiveHash;
        }

        public String getActivityHash() {
            return activityHash;
        }
    }
}

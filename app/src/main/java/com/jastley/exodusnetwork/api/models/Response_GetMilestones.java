package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jastley.exodusnetwork.Milestones.models.MilestoneModel;

import java.util.List;
import java.util.Map;

public class Response_GetMilestones {

    private List<MilestoneModel> milestoneList;
    private Throwable mError;
//    private String errorMessage;

    public Response_GetMilestones(List<MilestoneModel> milestones) {
        this.milestoneList = milestones;
        this.mError = null;
        this.message = null;
    }

    //Bungie error
    public Response_GetMilestones(String error) {
//        this.mError = error;
        this.message = error;
        this.milestoneList = null;
    }

    public List<MilestoneModel> getMilestoneList() {
        return milestoneList;
    }

    public void setMilestones(List<MilestoneModel> milestones) {
        this.milestoneList = milestones;
    }

    public Throwable getError() {
        return mError;
    }

    public void setError(Throwable t) {
        this.mError = t;
    }

    //JSON nodes

    @Expose
    @SerializedName("Response")
    private Map<String, MilestoneHashes> milestoneHashes;
    @Expose
    @SerializedName("ErrorCode")
    private String errorCode;
    @Expose
    @SerializedName("Message")
    private String message;

    public Map<String, MilestoneHashes> getMilestoneHashes() {
        return milestoneHashes;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    /** Milestone data **/

    public static class MilestoneHashes {

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

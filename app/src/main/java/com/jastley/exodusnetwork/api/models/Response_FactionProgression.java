package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Response_FactionProgression {

    @Expose
    @SerializedName("Response")
    private Response response;

    @Expose
    @SerializedName("ErrorCode")
    private String errorCode;

    @Expose
    @SerializedName("Message")
    private String message;

    public Response getResponse() {
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static class Response {
        @Expose
        @SerializedName("progressions")
        private Progressions progressions;

        @Expose
        @SerializedName("itemComponents")
        private ItemComponents itemComponents;

        public Progressions getProgressions() {
            return progressions;
        }



        public ItemComponents getItemComponents() {
            return itemComponents;
        }
    }

    public static class Progressions {

        @Expose
        @SerializedName("data")
        private Data data;

        public Data getData() {
            return data;
        }
    }

    public static class Data {

        @Expose
        @SerializedName("progressions")
        private Map<String, ProgressionsData> progressionsData;

        @Expose
        @SerializedName("factions")
        private Map<String, Factions> factions;

        public Map<String, ProgressionsData> getProgressionsData() {
            return progressionsData;
        }

        public Map<String, Factions> getFactions() {
            return factions;
        }
    }

    public static class ProgressionsData{
        @Expose
        @SerializedName("progressionHash")
        private String progressionHash;

        @Expose
        @SerializedName("dailyProgress")
        private String dailyProgress;

        @Expose
        @SerializedName("dailyLimit")
        private String dailyLimit;

        @Expose
        @SerializedName("weeklyProgress")
        private String weeklyProgress;

        @Expose
        @SerializedName("weeklyLimit")
        private String weeklyLimit;

        @Expose
        @SerializedName("currentProgress")
        private String currentProgress;

        @Expose
        @SerializedName("level")
        private String level;

        @Expose
        @SerializedName("levelCap")
        private String levelCap;

        @Expose
        @SerializedName("stepIndex")
        private String stepIndex;

        @Expose
        @SerializedName("progressToNextLevel")
        private String progressToNextLevel;

        public String getProgressionHash() {
            return progressionHash;
        }

        public String getDailyProgress() {
            return dailyProgress;
        }

        public String getDailyLimit() {
            return dailyLimit;
        }

        public String getWeeklyProgress() {
            return weeklyProgress;
        }

        public String getWeeklyLimit() {
            return weeklyLimit;
        }

        public String getCurrentProgress() {
            return currentProgress;
        }

        public String getLevel() {
            return level;
        }

        public String getLevelCap() {
            return levelCap;
        }

        public String getStepIndex() {
            return stepIndex;
        }

        public String getProgressToNextLevel() {
            return progressToNextLevel;
        }
    }

    public static class Factions {

        @Expose
        @SerializedName("factionHash")
        private String factionHash;

        @Expose
        @SerializedName("factionVendorIndex")
        private String factionVendorIndex;

        @Expose
        @SerializedName("progressionHash")
        private String progressionHash;

        @Expose
        @SerializedName("dailyProgress")
        private String dailyProgress;

        @Expose
        @SerializedName("dailyLimit")
        private String dailyLimit;

        @Expose
        @SerializedName("weeklyProgress")
        private String weeklyProgress;

        @Expose
        @SerializedName("weeklyLimit")
        private String weeklyLimit;

        @Expose
        @SerializedName("currentProgress")
        private String currentProgress;

        @Expose
        @SerializedName("level")
        private String level;

        @Expose
        @SerializedName("levelCap")
        private String levelCap;

        @Expose
        @SerializedName("stepIndex")
        private String stepIndex;

        @Expose
        @SerializedName("progressToNextLevel")
        private String progressToNextLevel;

        @Expose
        @SerializedName("nextLevelAt")
        private String nextLevelAt;

        public String getFactionHash() {
            return factionHash;
        }

        public String getFactionVendorIndex() {
            return factionVendorIndex;
        }

        public String getProgressionHash() {
            return progressionHash;
        }

        public String getDailyProgress() {
            return dailyProgress;
        }

        public String getDailyLimit() {
            return dailyLimit;
        }

        public String getWeeklyProgress() {
            return weeklyProgress;
        }

        public String getWeeklyLimit() {
            return weeklyLimit;
        }

        public String getCurrentProgress() {
            return currentProgress;
        }

        public String getLevel() {
            return level;
        }

        public String getLevelCap() {
            return levelCap;
        }

        public String getStepIndex() {
            return stepIndex;
        }

        public String getProgressToNextLevel() {
            return progressToNextLevel;
        }

        public String getNextLevelAt() {
            return nextLevelAt;
        }
    }

    public static class ItemComponents {

    }
}

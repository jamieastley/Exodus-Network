package com.jastley.exodusnetwork.api.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Response_GetProfileOverview {

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
        @SerializedName("profile")
        private Response_GetProfile.Profile profile;

        @Expose
        @SerializedName("characters")
        private Response_GetAllCharacters.Characters characters;

        @Expose
        @SerializedName("characterProgressions")
        private CharacterProgressions characterProgressions;

        @Expose
        @SerializedName("itemComponents")
        private ItemComponents itemComponents;

        public Response_GetProfile.Profile getProfile() {
            return profile;
        }

        public Response_GetAllCharacters.Characters getCharacters() {
            return characters;
        }

        public CharacterProgressions getCharacterProgressions() {
            return characterProgressions;
        }

        public ItemComponents getItemComponents() {
            return itemComponents;
        }
    }

    public static class CharacterProgressions {

        @Expose
        @SerializedName("data")
        private Map<String, Data> data;

        public Map<String, Data> getData() {
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

        @Expose
        @SerializedName("milestones")
        private Map<String, Milestones> milestones;

        @Expose
        @SerializedName("quests")
        private List<String> questList;

        @Expose
        @SerializedName("checklists")
        private Map<String, JsonObject> checklists;

        public Map<String, ProgressionsData> getProgressionsData() {
            return progressionsData;
        }

        public Map<String, Factions> getFactions() {
            return factions;
        }

        public Map<String, Milestones> getMilestones() {
            return milestones;
        }

        public List<String> getQuestList() {
            return questList;
        }

        public Map<String, JsonObject> getChecklists() {
            return checklists;
        }
    }

    public static class ProgressionsData{
        @Expose
        @SerializedName("progressionHash")
        private String progressionHash;

        @Expose
        @SerializedName("dailyProgress")
        private int dailyProgress;

        @Expose
        @SerializedName("dailyLimit")
        private int dailyLimit;

        @Expose
        @SerializedName("weeklyProgress")
        private int weeklyProgress;

        @Expose
        @SerializedName("weeklyLimit")
        private int weeklyLimit;

        @Expose
        @SerializedName("currentProgress")
        private int currentProgress;

        @Expose
        @SerializedName("level")
        private int level;

        @Expose
        @SerializedName("levelCap")
        private int levelCap;

        @Expose
        @SerializedName("stepIndex")
        private int stepIndex;

        @Expose
        @SerializedName("progressToNextLevel")
        private int progressToNextLevel;

        @Expose
        @SerializedName("nextLevelAt")
        private int nextLevelAt;

        public String getProgressionHash() {
            return progressionHash;
        }

        public int getDailyProgress() {
            return dailyProgress;
        }

        public int getDailyLimit() {
            return dailyLimit;
        }

        public int getWeeklyProgress() {
            return weeklyProgress;
        }

        public int getWeeklyLimit() {
            return weeklyLimit;
        }

        public int getCurrentProgress() {
            return currentProgress;
        }

        public int getLevel() {
            return level;
        }

        public int getLevelCap() {
            return levelCap;
        }

        public int getStepIndex() {
            return stepIndex;
        }

        public int getProgressToNextLevel() {
            return progressToNextLevel;
        }

        public int getNextLevelAt() {
            return nextLevelAt;
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

    public static class Milestones {

    }

    public static class ItemComponents {

    }
}

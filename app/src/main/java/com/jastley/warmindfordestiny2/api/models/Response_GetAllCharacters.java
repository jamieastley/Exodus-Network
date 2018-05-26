package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Response_GetAllCharacters {

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
        @SerializedName("characters")
        private Characters characters;

        public Characters getCharacters() {
            return characters;
        }
    }

    public static class Characters {
        @Expose
        @SerializedName("data")
        private Map<String, CharacterData> data;

        public Map<String, CharacterData> getData() {
            return data;
        }
    }

    public static class CharacterData {
        @Expose
        @SerializedName("membershipId")
        private String membershipId;
        @Expose
        @SerializedName("membershipType")
        private String membershipType;
        @Expose
        @SerializedName("characterId")
        private String characterId;
        @Expose
        @SerializedName("dateLastPlayed")
        private String dateLastPlayed;
        @Expose
        @SerializedName("minutesPlayedThisSession")
        private String minutesPlayedThisSession;
        @Expose
        @SerializedName("minutesPlayedTotal")
        private String minutesPlayedTotal;
        @Expose
        @SerializedName("light")
        private String light;

        //stats removed from here

        @Expose
        @SerializedName("raceHash")
        private String raceHash;
        @Expose
        @SerializedName("genderHash")
        private String genderHash;
        @Expose
        @SerializedName("classHash")
        private String classHash;
        @Expose
        @SerializedName("classType")
        private String classType;
        @Expose
        @SerializedName("genderType")
        private String genderType;
        @Expose
        @SerializedName("emblemPath")
        private String emblemPath;
        @Expose
        @SerializedName("emblemBackgroundPath")
        private String emblemBackgroundPath;
        @Expose
        @SerializedName("emblemHash")
        private String emblemHash;
        @Expose
        @SerializedName("emblemColor")
        private EmblemColor emblemColor;
        @Expose
        @SerializedName("levelProgression")
        private LevelProgression levelProgression;
        @Expose
        @SerializedName("baseCharacterLevel")
        private String baseCharacterLevel;
        @Expose
        @SerializedName("percentToNextLevel")
        private String percentToNextLevel;

        public String getMembershipId() {
            return membershipId;
        }

        public String getMembershipType() {
            return membershipType;
        }

        public String getCharacterId() {
            return characterId;
        }

        public String getDateLastPlayed() {
            return dateLastPlayed;
        }

        public String getMinutesPlayedThisSession() {
            return minutesPlayedThisSession;
        }

        public String getMinutesPlayedTotal() {
            return minutesPlayedTotal;
        }

        public String getLight() {
            return light;
        }

        public String getRaceHash() {
            return raceHash;
        }

        public String getGenderHash() {
            return genderHash;
        }

        public String getClassHash() {
            return classHash;
        }

        public String getClassType() {
            return classType;
        }

        public String getGenderType() {
            return genderType;
        }

        public String getEmblemPath() {
            return emblemPath;
        }

        public String getEmblemBackgroundPath() {
            return emblemBackgroundPath;
        }

        public String getEmblemHash() {
            return emblemHash;
        }

        public EmblemColor getEmblemColor() {
            return emblemColor;
        }

        public LevelProgression getLevelProgression() {
            return levelProgression;
        }

        public String getBaseCharacterLevel() {
            return baseCharacterLevel;
        }

        public String getPercentToNextLevel() {
            return percentToNextLevel;
        }
    }

    public static class EmblemColor {
        @Expose
        @SerializedName("red")
        private String red;
        @Expose
        @SerializedName("green")
        private String green;
        @Expose
        @SerializedName("blue")
        private String blue;

        public String getRed() {
            return red;
        }

        public String getGreen() {
            return green;
        }

        public String getBlue() {
            return blue;
        }
    }

    public static class LevelProgression {
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
}

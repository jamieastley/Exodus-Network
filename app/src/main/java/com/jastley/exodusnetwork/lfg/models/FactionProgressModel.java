package com.jastley.exodusnetwork.lfg.models;

import com.jastley.exodusnetwork.Utils.UnsignedHashConverter;

public class FactionProgressModel {

    private String factionName;
    private String factionHash;
    private String progressionHash;
    private String currentProgress;
    private String level;
    private String progressToNextLevel;
    private String nextLevelAt;
    private String factionIconUrl;
    private String primaryKey;

    public FactionProgressModel() {
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public String getFactionHash() {
        return factionHash;
    }

    public void setFactionHash(String factionHash) {
        this.factionHash = factionHash;
    }

    public String getProgressionHash() {
        return progressionHash;
    }

    public void setProgressionHash(String progressionHash) {
        this.progressionHash = progressionHash;
    }

    public String getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(String currentProgress) {
        this.currentProgress = currentProgress;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgressToNextLevel() {
        return progressToNextLevel;
    }

    public void setProgressToNextLevel(String progressToNextLevel) {
        this.progressToNextLevel = progressToNextLevel;
    }

    public String getNextLevelAt() {
        return nextLevelAt;
    }

    public void setNextLevelAt(String nextLevelAt) {
        this.nextLevelAt = nextLevelAt;
    }

    public String getFactionIconUrl() {
        return factionIconUrl;
    }

    public void setFactionIconUrl(String factionIconUrl) {
        this.factionIconUrl = factionIconUrl;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String key) {

        Long converted = Long.valueOf(key);
        if(converted > 2147483647L){
            this.primaryKey = UnsignedHashConverter.convert(converted);
        }
        else {
            this.primaryKey = key;
        }
    }
}

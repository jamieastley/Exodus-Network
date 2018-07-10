package com.jastley.warmindfordestiny2.Milestones.models;

public class MilestoneModel {

    private String milestoneName;
    private String milestoneDescription;
    private String milestoneImageURL;
    private String milestoneRewardHash;
    private String milestoneRewardName;
    private String milestoneRewardImageURL;
    private String milestoneHash;

    private Throwable mError;

    //Room/database
    private String primaryKey;

    //Flashpoint
    private String questItemHash;

    public MilestoneModel() {
    }

    public MilestoneModel(Throwable error) {
        this.mError = error;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public void setMilestoneDescription(String milestoneDescription) {
        this.milestoneDescription = milestoneDescription;
    }

    public String getMilestoneImageURL() {
        return milestoneImageURL;
    }

    public void setMilestoneImageURL(String milestoneImageURL) {
        this.milestoneImageURL = milestoneImageURL;
    }

    public String getMilestoneRewardName() {
        return milestoneRewardName;
    }

    public void setMilestoneRewardName(String milestoneRewardName) {
        this.milestoneRewardName = milestoneRewardName;
    }

    public String getMilestoneRewardImageURL() {
        return milestoneRewardImageURL;
    }

    public void setMilestoneRewardImageURL(String milestoneRewardImageURL) {
        this.milestoneRewardImageURL = milestoneRewardImageURL;
    }

    public void setMilestoneHash(String milestoneHash) {
        this.milestoneHash = milestoneHash;
    }

    public String getMilestoneHash() {
        return milestoneHash;
    }

    public String getMilestoneRewardHash() {
        return milestoneRewardHash;
    }

    public void setMilestoneRewardHash(String milestoneRewardHash) {
        this.milestoneRewardHash = milestoneRewardHash;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getQuestItemHash() {
        return questItemHash;
    }

    public void setQuestItemHash(String questItemHash) {
        this.questItemHash = questItemHash;
    }
}

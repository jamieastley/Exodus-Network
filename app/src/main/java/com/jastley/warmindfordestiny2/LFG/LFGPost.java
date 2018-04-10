package com.jastley.warmindfordestiny2.LFG;

import com.google.gson.JsonObject;

/**
 * Created by jastl on 2/03/2018.
 */

public class LFGPost {

    private String activityTitle;
    private String activityCheckpoint;
    private String lightLevel;
    private String membershipType;
    private String displayName;
    private String classType;
    private String description;
    private Long dateTime;
    private boolean hasMic;
    private JsonObject characterStats;

    public LFGPost() {
        //empty constructor for Firebase
    }

    //TODO: add description

    public LFGPost(String activityTitle, String activityCheckpoint, String lightLevel, String membershipType, String displayName, String classType, String description, Long dateTime, boolean hasMic, JsonObject characterStats) {
        this.activityTitle = activityTitle;
        this.activityCheckpoint = activityCheckpoint;
        this.lightLevel = lightLevel;
        this.membershipType = membershipType;
        this.displayName = displayName;
        this.classType = classType;
        this.description = description;
        this.dateTime = dateTime;
        this.hasMic = hasMic;
        this.characterStats = characterStats;
    }


    public String getActivityTitle() {
        return activityTitle;
    }

    public String getActivityCheckpoint() {
        return activityCheckpoint;
    }

    public String getLightLevel() {
        return lightLevel;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getClassType() {
        return classType;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public boolean isHasMic() {
        return hasMic;
    }

    public JsonObject getCharacterStats() {
        return characterStats;
    }

    public void setCharacterStats(JsonObject characterStats) {
        this.characterStats = characterStats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

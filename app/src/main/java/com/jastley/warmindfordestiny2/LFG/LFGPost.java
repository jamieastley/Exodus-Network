package com.jastley.warmindfordestiny2.LFG;

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
    private String membershipId;
    private String emblemIcon;
    private String emblemBackground;
    private String characterId;

    public LFGPost() {
        //empty constructor for Firebase
    }

    //TODO: add description

    public LFGPost(String activityTitle, String activityCheckpoint, String lightLevel, String membershipType,
                   String displayName, String classType, String description, Long dateTime, boolean hasMic,
                   String membershipId, String emblemIcon, String emblemBackground, String characterId) {
        this.activityTitle = activityTitle;
        this.activityCheckpoint = activityCheckpoint;
        this.lightLevel = lightLevel;
        this.membershipType = membershipType;
        this.displayName = displayName;
        this.classType = classType;
        this.description = description;
        this.dateTime = dateTime;
        this.hasMic = hasMic;
        this.membershipId = membershipId;
        this.emblemIcon = emblemIcon;
        this.emblemBackground = emblemBackground;
        this.characterId = characterId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getEmblemIcon() {
        return emblemIcon;
    }

    public void setEmblemIcon(String emblemIcon) {
        this.emblemIcon = emblemIcon;
    }

    public String getEmblemBackground() {
        return emblemBackground;
    }

    public void setEmblemBackground(String emblemBackground) {
        this.emblemBackground = emblemBackground;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }
}

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
    private String dateTime;
    private boolean hasMic;

    public LFGPost() {
        //empty constructor for Firebase
    }

    public LFGPost(String activityTitle, String activityCheckpoint, String lightLevel, String membershipType, String displayName, String classType, String dateTime, boolean hasMic) {
        this.activityTitle = activityTitle;
        this.activityCheckpoint = activityCheckpoint;
        this.lightLevel = lightLevel;
        this.membershipType = membershipType;
        this.displayName = displayName;
        this.classType = classType;
        this.dateTime = dateTime;
        this.hasMic = hasMic;
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

    public String getDateTime() {
        return dateTime;
    }

    public boolean isHasMic() {
        return hasMic;
    }
}

package com.jastley.exodusnetwork.lfg.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jamie1192 on 2/03/2018.
 */

public class LFGPost implements Parcelable {

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


    protected LFGPost(Parcel in) {
        activityTitle = in.readString();
        activityCheckpoint = in.readString();
        lightLevel = in.readString();
        membershipType = in.readString();
        displayName = in.readString();
        classType = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            dateTime = null;
        } else {
            dateTime = in.readLong();
        }
        hasMic = in.readByte() != 0;
        membershipId = in.readString();
        emblemIcon = in.readString();
        emblemBackground = in.readString();
        characterId = in.readString();
    }

    public static final Creator<LFGPost> CREATOR = new Creator<LFGPost>() {
        @Override
        public LFGPost createFromParcel(Parcel in) {
            return new LFGPost(in);
        }

        @Override
        public LFGPost[] newArray(int size) {
            return new LFGPost[size];
        }
    };

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

    public boolean getHasMic() {
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

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public void setActivityCheckpoint(String activityCheckpoint) {
        this.activityCheckpoint = activityCheckpoint;
    }

    public void setLightLevel(String lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public void setHasMic(boolean hasMic) {
        this.hasMic = hasMic;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityTitle);
        dest.writeString(activityCheckpoint);
        dest.writeString(lightLevel);
        dest.writeString(membershipType);
        dest.writeString(displayName);
        dest.writeString(classType);
        dest.writeString(description);
        if (dateTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dateTime);
        }
        dest.writeByte((byte) (hasMic ? 1 : 0));
        dest.writeString(membershipId);
        dest.writeString(emblemIcon);
        dest.writeString(emblemBackground);
        dest.writeString(characterId);
    }
}

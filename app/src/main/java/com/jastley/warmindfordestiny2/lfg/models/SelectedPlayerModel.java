package com.jastley.warmindfordestiny2.lfg.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jamie1192 on 16/4/18.
 */

public class SelectedPlayerModel implements Parcelable {

    private String displayName;
    private String membershipId;
    private String membershipType;
    private String lightLevel;
    private String characterId;
    private String classType;
    private String emblemIcon;
    private String emblemBackground;

    public SelectedPlayerModel(){

    }

    public SelectedPlayerModel(String displayName, String membershipId, String membershipType, String lightLevel, String characterId, String classType, String emblemIcon, String emblemBackground) {
        this.displayName = displayName;
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.lightLevel = lightLevel;
        this.characterId = characterId;
        this.classType = classType;
        this.emblemIcon = emblemIcon;
        this.emblemBackground = emblemBackground;
    }

    protected SelectedPlayerModel(Parcel in) {
        displayName = in.readString();
        membershipId = in.readString();
        membershipType = in.readString();
        characterId = in.readString();
        classType = in.readString();
        emblemIcon = in.readString();
        emblemBackground = in.readString();
    }

    public static final Creator<SelectedPlayerModel> CREATOR = new Creator<SelectedPlayerModel>() {
        @Override
        public SelectedPlayerModel createFromParcel(Parcel in) {
            return new SelectedPlayerModel(in);
        }

        @Override
        public SelectedPlayerModel[] newArray(int size) {
            return new SelectedPlayerModel[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(membershipId);
        dest.writeString(characterId);
        dest.writeString(classType);
        dest.writeString(emblemIcon);
        dest.writeString(emblemBackground);
    }

    public String getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(String lightLevel) {
        this.lightLevel = lightLevel;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}

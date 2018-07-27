package com.jastley.exodusnetwork.Inventory.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jamie1192 on 23/4/18.
 */

public class CharacterDatabaseModel implements Parcelable {

    private String membershipId;
    private String characterId;
    private String membershipType;
    private String classType;
    private String emblemIcon;
    private String emblemBackground;
    private String baseCharacterLevel;
    private String lightLevel;

    public CharacterDatabaseModel(String membershipId, String characterId, String membershipType,
                                  String classType, String emblemIcon, String emblemBackground,
                                  String characterLevel, String light) {
        this.membershipId = membershipId;
        this.characterId = characterId;
        this.membershipType = membershipType;
        this.classType = classType;
        this.emblemIcon = emblemIcon;
        this.emblemBackground = emblemBackground;
        this.baseCharacterLevel = characterLevel;
        this.lightLevel = light;
    }

    public CharacterDatabaseModel() {

    }

    protected CharacterDatabaseModel(Parcel in) {
        membershipId = in.readString();
        characterId = in.readString();
        membershipType = in.readString();
        classType = in.readString();
    }

    public static final Creator<CharacterDatabaseModel> CREATOR = new Creator<CharacterDatabaseModel>() {
        @Override
        public CharacterDatabaseModel createFromParcel(Parcel in) {
            return new CharacterDatabaseModel(in);
        }

        @Override
        public CharacterDatabaseModel[] newArray(int size) {
            return new CharacterDatabaseModel[size];
        }
    };

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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
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

    public String getBaseCharacterLevel() {
        return baseCharacterLevel;
    }

    public void setBaseCharacterLevel(String baseCharacterLevel) {
        this.baseCharacterLevel = baseCharacterLevel;
    }

    public String getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(String lightLevel) {
        this.lightLevel = lightLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(membershipId);
        dest.writeString(characterId);
        dest.writeString(membershipType);
        dest.writeString(classType);
    }
}

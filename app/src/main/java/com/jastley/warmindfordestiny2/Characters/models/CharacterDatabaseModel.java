package com.jastley.warmindfordestiny2.Characters.models;

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
    private String emblemBackground;

    public CharacterDatabaseModel(String membershipId, String characterId, String membershipType, String classType, String emblem) {
        this.membershipId = membershipId;
        this.characterId = characterId;
        this.membershipType = membershipType;
        this.classType = classType;
        this.emblemBackground = emblem;
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
        dest.writeString(membershipId);
        dest.writeString(characterId);
        dest.writeString(membershipType);
        dest.writeString(classType);
    }
}

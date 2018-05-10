package com.jastley.warmindfordestiny2.api.models;

public class EquipItemRequestBody {

    private String itemId;
    private String characterId;
    private String membershipType;

    public EquipItemRequestBody(String itemId, String characterId, String membershipType) {
        this.itemId = itemId;
        this.characterId = characterId;
        this.membershipType = membershipType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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
}

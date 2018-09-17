package com.jastley.exodusnetwork.api.models;

public class PostmasterTransferRequest {

    private String itemReferenceHash;
    private String stackSize;
    private String itemId;
    private String characterId;
    private String membershipType;

    public PostmasterTransferRequest(String itemReferenceHash, String stackSize, String itemId, String characterId, String mType) {
        this.itemReferenceHash = itemReferenceHash;
        this.stackSize = stackSize;
        this.itemId = itemId;
        this.characterId = characterId;
        this.membershipType = mType;
    }

    public PostmasterTransferRequest() {
    }

    public String getItemReferenceHash() {
        return itemReferenceHash;
    }

    public void setItemReferenceHash(String itemReferenceHash) {
        this.itemReferenceHash = itemReferenceHash;
    }

    public String getStackSize() {
        return stackSize;
    }

    public void setStackSize(String stackSize) {
        this.stackSize = stackSize;
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

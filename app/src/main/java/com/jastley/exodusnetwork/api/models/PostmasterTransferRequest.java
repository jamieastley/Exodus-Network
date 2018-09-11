package com.jastley.exodusnetwork.api.models;

public class PostmasterTransferRequest {

    private String itemReferenceHash;
    private String stackSize;
    private String itemId;
    private String characterId;

    public PostmasterTransferRequest(String itemReferenceHash, String stackSize, String itemId, String characterId) {
        this.itemReferenceHash = itemReferenceHash;
        this.stackSize = stackSize;
        this.itemId = itemId;
        this.characterId = characterId;
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
}

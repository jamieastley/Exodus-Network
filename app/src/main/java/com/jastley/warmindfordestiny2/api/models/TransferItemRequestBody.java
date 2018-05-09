package com.jastley.warmindfordestiny2.api.models;

public class TransferItemRequestBody {

    private String itemReferenceHash;
    private String stackSize;
    private boolean transferToVault;
    private String itemId;
    private String membershipType;
    private String characterId;

    public TransferItemRequestBody(String itemReferenceHash, String stackSize, boolean transferToVault, String itemId, String membershipType, String characterId) {
        this.itemReferenceHash = itemReferenceHash;
        this.stackSize = stackSize;
        this.transferToVault = transferToVault;
        this.itemId = itemId;
        this.membershipType = membershipType;
        this.characterId = characterId;
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

    public boolean isTransferToVault() {
        return transferToVault;
    }

    public void setTransferToVault(boolean transferToVault) {
        this.transferToVault = transferToVault;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }
}

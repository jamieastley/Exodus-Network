package com.jastley.exodusnetwork.api.models;

public class ItemLockStateBody {

    private boolean state;
    private String itemId;
    private String characterId;
    private String membershipId;

    public ItemLockStateBody(boolean state, String itemId, String characterId, String membershipId) {
        this.state = state;
        this.itemId = itemId;
        this.characterId = characterId;
        this.membershipId = membershipId;
    }
}

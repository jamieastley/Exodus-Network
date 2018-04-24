package com.jastley.warmindfordestiny2.Characters.models;

/**
 * Created by jamie1192 on 24/4/18.
 */

public class InventoryItemModel {

    private Double itemHash;
    private String itemInstanceId;
    private Double bucketHash;
    //TODO: state (is item locked?)

    //item instance data
    private boolean isEquipped;
    private boolean canEquip;
    private int primaryStatValue;

    public InventoryItemModel(Double itemHash, String itemInstanceId, Double bucketHash, int primaryStatValue) {
        this.itemHash = itemHash;
        this.itemInstanceId = itemInstanceId;
        this.bucketHash = bucketHash;
        this.primaryStatValue = primaryStatValue;
    }

    public InventoryItemModel() {
    }

    public Double getItemHash() {
        return itemHash;
    }

    public void setItemHash(Double itemHash) {
        this.itemHash = itemHash;
    }

    public String getItemInstanceId() {
        return itemInstanceId;
    }

    public void setItemInstanceId(String itemInstanceId) {
        this.itemInstanceId = itemInstanceId;
    }

    public Double getBucketHash() {
        return bucketHash;
    }

    public void setBucketHash(Double bucketHash) {
        this.bucketHash = bucketHash;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    public boolean isCanEquip() {
        return canEquip;
    }

    public void setCanEquip(boolean canEquip) {
        this.canEquip = canEquip;
    }

    public int getPrimaryStatValue() {
        return primaryStatValue;
    }

    public void setPrimaryStatValue(int primaryStatValue) {
        this.primaryStatValue = primaryStatValue;
    }
}

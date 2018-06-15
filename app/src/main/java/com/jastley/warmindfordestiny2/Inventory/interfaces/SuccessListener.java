package com.jastley.warmindfordestiny2.Inventory.interfaces;

public interface SuccessListener {
    void inProgress(String message);
    void onAuthFail();
    void onSuccess(int position, boolean wasSuccessful, String message, boolean isTransfer);
    void onEquipSameCharacter(int position, boolean wasSuccessful, String message, boolean isTransfer);
}

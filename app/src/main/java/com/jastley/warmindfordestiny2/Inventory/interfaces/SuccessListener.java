package com.jastley.warmindfordestiny2.Inventory.interfaces;

public interface SuccessListener {
    void inProgress();
    void onSuccess(int position, boolean wasSuccessful, String message, boolean isTransfer);
}

package com.jastley.exodusnetwork.Inventory.models;

public class TransferEquipStatus {

    private String message;
    private Throwable throwable;
    private boolean isComplete;

    public TransferEquipStatus(String message) {
        this.message = message;
    }

    public TransferEquipStatus(Throwable throwable) {
        this.throwable = throwable;
    }

    public TransferEquipStatus(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public boolean isComplete() {
        return isComplete;
    }
}

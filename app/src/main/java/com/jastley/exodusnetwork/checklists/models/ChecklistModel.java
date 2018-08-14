package com.jastley.exodusnetwork.checklists.models;

public class ChecklistModel {

    private String categoryHash;
    private String hash;
    private boolean isCompleted;
    private int position;
    private String checklistItemName;

    public ChecklistModel(String hash, boolean isCompleted, int count, String categoryHash) {
        this.hash = hash;
        this.isCompleted = isCompleted;
        this.position = count;
        this.categoryHash = categoryHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getCategoryHash() {
        return categoryHash;
    }

    public void setCategoryHash(String categoryHash) {
        this.categoryHash = categoryHash;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setChecklistItemName(String checklistItemName) {
        this.checklistItemName = checklistItemName;
    }

    public String getChecklistItemName() {
        return checklistItemName;
    }
}

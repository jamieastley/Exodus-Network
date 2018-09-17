package com.jastley.exodusnetwork.checklists.models;

public class ChecklistTabModel {

    private String tabTitle;
    private String checklistHash;

    public ChecklistTabModel(String title, String hash) {
        this.tabTitle = title;
        this.checklistHash = hash;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getChecklistHash() {
        return checklistHash;
    }

    public void setChecklistHash(String checklistHash) {
        this.checklistHash = checklistHash;
    }
}

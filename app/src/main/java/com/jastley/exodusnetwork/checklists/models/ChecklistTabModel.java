package com.jastley.exodusnetwork.checklists.models;

public class ChecklistTabModel {

    private String tabTitle;
    private String checklistHash;
    private int iconRes;

    public ChecklistTabModel(String title, String hash, int icon) {
        this.tabTitle = title;
        this.checklistHash = hash;
        this.iconRes = icon;
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

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}

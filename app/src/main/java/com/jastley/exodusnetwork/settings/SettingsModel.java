package com.jastley.exodusnetwork.settings;

public class SettingsModel {

    private String title;
    private String message;

    public SettingsModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}

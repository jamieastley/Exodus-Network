package com.jastley.warmindfordestiny2.Account.models;

public class AccountStatsModel {

    private String statName;
    private String statValue;

    public AccountStatsModel(String statName, String statValue) {
        this.statName = statName;
        this.statValue = statValue;
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getStatValue() {
        return statValue;
    }

    public void setStatValue(String statValue) {
        this.statValue = statValue;
    }
}

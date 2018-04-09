package com.jastley.warmindfordestiny2.database;

/**
 * Created by jamie on 1/4/18.
 */

public class OldDatabaseModel {

    public static final String TABLE_NAME = "account";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_VALUE = "value";

    private String key;
    private String value;

    //Create table
    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "("+ COLUMN_KEY + " VARCHAR PRIMARY KEY,"
                    + COLUMN_VALUE + " VARCHAR"
                    + ")";

    public OldDatabaseModel(){

    }

    public OldDatabaseModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

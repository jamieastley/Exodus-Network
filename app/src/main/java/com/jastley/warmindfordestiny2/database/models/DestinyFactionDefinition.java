package com.jastley.warmindfordestiny2.database.models;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import com.jastley.warmindfordestiny2.database.Converter;

import java.sql.Blob;

@Entity
public class DestinyFactionDefinition {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "json")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}

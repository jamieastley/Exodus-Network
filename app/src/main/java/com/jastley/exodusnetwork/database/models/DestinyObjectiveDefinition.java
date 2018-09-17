package com.jastley.exodusnetwork.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.database.converters.ObjectiveDefinitionConverter;
import com.jastley.exodusnetwork.database.jsonModels.ObjectiveJsonData;

@Entity
@TypeConverters({ObjectiveDefinitionConverter.class})
public class DestinyObjectiveDefinition {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "json")
    private ObjectiveJsonData value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectiveJsonData getValue() {
        return value;
    }

    public void setValue(ObjectiveJsonData value) {
        this.value = value;
    }
}

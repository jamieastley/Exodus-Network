package com.jastley.exodusnetwork.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

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

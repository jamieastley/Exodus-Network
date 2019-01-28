package com.jastley.exodusnetwork.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.jastley.exodusnetwork.database.converters.CollectibleDefinitionConverter;
import com.jastley.exodusnetwork.database.jsonModels.CollectibleJsonData;

@Entity
@TypeConverters({CollectibleDefinitionConverter.class})
public class DestinyCollectibleDefinition {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "json")
    private CollectibleJsonData value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CollectibleJsonData getValue() {
        return value;
    }

    public void setValue(CollectibleJsonData value) {
        this.value = value;
    }
}

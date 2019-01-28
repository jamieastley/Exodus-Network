package com.jastley.exodusnetwork.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.database.converters.AccountDataConverter;

/**
 * Created by jamie on 9/4/18.
 */

@Entity
@TypeConverters({AccountDataConverter.class})
public class Account {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "value")
    private Response_GetAllCharacters.CharacterData value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Response_GetAllCharacters.CharacterData getValue() {
        return value;
    }

    public void setValue(Response_GetAllCharacters.CharacterData value) {
        this.value = value;
    }

}





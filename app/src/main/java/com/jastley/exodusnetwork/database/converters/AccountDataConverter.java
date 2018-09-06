package com.jastley.exodusnetwork.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;

public class AccountDataConverter {

    @TypeConverter
    public static Response_GetAllCharacters.CharacterData fromJsonString(String data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.fromJson(data, Response_GetAllCharacters.CharacterData.class);
    }

    @TypeConverter
    public static String toString (Response_GetAllCharacters.CharacterData data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.toJson(data);
    }
}

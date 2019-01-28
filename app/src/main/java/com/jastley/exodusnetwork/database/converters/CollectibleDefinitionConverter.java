package com.jastley.exodusnetwork.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.database.jsonModels.CollectibleJsonData;

public class CollectibleDefinitionConverter {

    @TypeConverter
    public static CollectibleJsonData fromJsonString(String data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.fromJson(data, CollectibleJsonData.class);
    }

    @TypeConverter
    public static String toString (CollectibleJsonData data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.toJson(data);
    }
}

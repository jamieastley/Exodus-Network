package com.jastley.exodusnetwork.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.database.jsonModels.ObjectiveJsonData;

public class ObjectiveDefinitionConverter {

    @TypeConverter
    public static ObjectiveJsonData fromJsonString(String data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.fromJson(data, ObjectiveJsonData.class);
    }

    @TypeConverter
    public static String toString (ObjectiveJsonData data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.toJson(data);
    }
}

package com.jastley.exodusnetwork.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

public class InventoryItemDefinitionConverter {

    @TypeConverter
    public static InventoryItemJsonData fromJsonString(String data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.fromJson(data, InventoryItemJsonData.class);
    }

    @TypeConverter
    public static String toString (InventoryItemJsonData data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.toJson(data);
    }
}

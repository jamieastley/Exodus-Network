package com.jastley.exodusnetwork.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemData;

public class InventoryItemDefinitionConverter {

    @TypeConverter
    public static InventoryItemData fromJsonString(String data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.fromJson(data, InventoryItemData.class);
    }

    @TypeConverter
    public static String toString (InventoryItemData data) {
        Gson gson = new GsonBuilder().create();
        return data == null ? null : gson.toJson(data);
    }
}

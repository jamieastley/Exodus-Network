package com.jastley.exodusnetwork.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.database.converters.InventoryItemDefinitionConverter;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemData;

/**
 * Created by jamie on 9/4/18.
 */

@Entity
@TypeConverters({InventoryItemDefinitionConverter.class})
public class DestinyInventoryItemDefinition implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "json")
    private InventoryItemData value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryItemData getValue() {
        return value;
    }

    public void setValue(InventoryItemData value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

package com.jastley.exodusnetwork.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.jastley.exodusnetwork.database.converters.InventoryItemDefinitionConverter;
import com.jastley.exodusnetwork.database.jsonModels.InventoryItemJsonData;

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
    private InventoryItemJsonData value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryItemJsonData getValue() {
        return value;
    }

    public void setValue(InventoryItemJsonData value) {
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

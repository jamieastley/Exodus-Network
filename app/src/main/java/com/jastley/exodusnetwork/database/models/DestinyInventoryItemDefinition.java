package com.jastley.exodusnetwork.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.jastley.exodusnetwork.Inventory.models.InventoryItemModel;
import com.jastley.exodusnetwork.Milestones.models.InventoryDataModel;
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

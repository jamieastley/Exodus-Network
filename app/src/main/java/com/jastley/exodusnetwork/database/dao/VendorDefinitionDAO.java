package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyVendorDefinition;

import java.util.List;

@Dao
public interface VendorDefinitionDAO {

    @Query("SELECT * FROM DestinyVendorDefinition")
    LiveData<DestinyVendorDefinition> getAllVendorDefinitions();

    @Query("SELECT * FROM DestinyVendorDefinition WHERE id = :key")
    LiveData<DestinyVendorDefinition> getVendorDefinition(String key);

    @Query("SELECT * FROM DestinyVendorDefinition WHERE id IN (:key)")
    LiveData<List<DestinyVendorDefinition>> getVendorDefinitionList(List<String> key);

}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyVendorGroupDefinition;

import java.util.List;

@Dao
public interface VendorGroupDefinitionDAO {

    @Query("SELECT * FROM DestinyVendorGroupDefinition")
    LiveData<DestinyVendorGroupDefinition> getAllVendorGroupDefinitions();

    @Query("SELECT * FROM DestinyVendorGroupDefinition WHERE id = :key")
    LiveData<DestinyVendorGroupDefinition> getVendorGroupDefinition(String key);

    @Query("SELECT * FROM DestinyVendorGroupDefinition WHERE id IN (:key)")
    LiveData<List<DestinyVendorGroupDefinition>> getVendorDefinitionList(List<String> key);

}

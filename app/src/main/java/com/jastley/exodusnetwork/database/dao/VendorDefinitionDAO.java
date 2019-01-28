package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyVendorDefinition;

import java.util.List;

@Dao
public interface VendorDefinitionDAO {

    @Query("SELECT * FROM DestinyVendorDefinition")
    Maybe<DestinyVendorDefinition> getAllVendorDefinitions();

    @Query("SELECT * FROM DestinyVendorDefinition WHERE id = :key")
    Maybe<DestinyVendorDefinition> getVendorDefinition(String key);

    @Query("SELECT * FROM DestinyVendorDefinition WHERE id IN (:key)")
    Maybe<List<DestinyVendorDefinition>> getVendorDefinitionList(List<String> key);

}

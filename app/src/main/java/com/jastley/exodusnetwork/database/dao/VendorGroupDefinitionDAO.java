package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyVendorGroupDefinition;

import java.util.List;

@Dao
public interface VendorGroupDefinitionDAO {

    @Query("SELECT * FROM DestinyVendorGroupDefinition")
    Maybe<DestinyVendorGroupDefinition> getAllVendorGroupDefinitions();

    @Query("SELECT * FROM DestinyVendorGroupDefinition WHERE id = :key")
    Maybe<DestinyVendorGroupDefinition> getVendorGroupDefinition(String key);

    @Query("SELECT * FROM DestinyVendorGroupDefinition WHERE id IN (:key)")
    Maybe<List<DestinyVendorGroupDefinition>> getVendorDefinitionList(List<String> key);

}

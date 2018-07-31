package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyLocationDefinition;

import java.util.List;

@Dao
public interface LocationDefinitionDAO {

    @Query("SELECT * FROM DestinyLocationDefinition")
    LiveData<DestinyLocationDefinition> getAllLocationDefinitions();

    @Query("SELECT * FROM DestinyLocationDefinition WHERE id = :key")
    LiveData<DestinyLocationDefinition> getLocationDefinition(String key);

    @Query("SELECT * FROM DestinyLocationDefinition WHERE id IN (:key)")
    LiveData<List<DestinyLocationDefinition>> getLocationDefinitionList(List<String> key);
}

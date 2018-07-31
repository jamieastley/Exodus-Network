package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyProgressionDefinition;

import java.util.List;

@Dao
public interface ProgressionDefinitionDAO {

    @Query("SELECT * FROM DestinyProgressionDefinition")
    LiveData<DestinyProgressionDefinition> getAllProgressionDefinitions();

    @Query("SELECT * FROM DestinyProgressionDefinition WHERE id = :key")
    LiveData<DestinyProgressionDefinition> getProgressionDefinitions(String key);

    @Query("SELECT * FROM DestinyProgressionDefinition WHERE id IN (:key)")
    LiveData<List<DestinyProgressionDefinition>> getProgressionDefinitionList(List<String> key);
}

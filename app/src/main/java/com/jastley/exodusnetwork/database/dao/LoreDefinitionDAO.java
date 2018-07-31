package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyLoreDefinition;

import java.util.List;

@Dao
public interface LoreDefinitionDAO {

    @Query("SELECT * FROM DestinyLoreDefinition")
    LiveData<DestinyLoreDefinition> getAllLoreDefinitions();

    @Query("SELECT * FROM DestinyLoreDefinition WHERE id = :key")
    LiveData<DestinyLoreDefinition> getLoreDefinition(String key);

    @Query("SELECT * FROM DestinyLoreDefinition WHERE id IN (:key)")
    LiveData<List<DestinyLoreDefinition>> getLoreDefinitionList(List<String> key);
}

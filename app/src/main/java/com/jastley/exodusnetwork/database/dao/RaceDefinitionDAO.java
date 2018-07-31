package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyRaceDefinition;

import java.util.List;

@Dao
public interface RaceDefinitionDAO {

    @Query("SELECT * FROM DestinyRaceDefinition")
    LiveData<DestinyRaceDefinition> getAllRaceDefinitions();

    @Query("SELECT * FROM DestinyRaceDefinition WHERE id = :key")
    LiveData<DestinyRaceDefinition> getRaceDefinition(String key);

    @Query("SELECT * FROM DestinyRaceDefinition WHERE id IN (:key)")
    LiveData<List<DestinyRaceDefinition>> getRaceDefinitionList(List<String> key);
}

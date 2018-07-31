package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyEnemyRaceDefinition;

import java.util.List;

@Dao
public interface EnemyRaceDefinitionDAO {

    @Query("SELECT * FROM DestinyEnemyRaceDefinition")
    LiveData<DestinyEnemyRaceDefinition> getEnemyRaceDefinitions();

    @Query("SELECT * FROM DestinyEnemyRaceDefinition WHERE id = :key")
    LiveData<DestinyEnemyRaceDefinition> getEnemyRaceDefinition(String key);

    @Query("SELECT * FROM DestinyEnemyRaceDefinition WHERE id IN (:key)")
    LiveData<List<DestinyEnemyRaceDefinition>> getEnemyRaceDefintionList(List<String> key);

}

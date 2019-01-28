package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyEnemyRaceDefinition;

import java.util.List;

@Dao
public interface EnemyRaceDefinitionDAO {

    @Query("SELECT * FROM DestinyEnemyRaceDefinition")
    Maybe<DestinyEnemyRaceDefinition> getEnemyRaceDefinitions();

    @Query("SELECT * FROM DestinyEnemyRaceDefinition WHERE id = :key")
    Maybe<DestinyEnemyRaceDefinition> getEnemyRaceDefinition(String key);

    @Query("SELECT * FROM DestinyEnemyRaceDefinition WHERE id IN (:key)")
    Maybe<List<DestinyEnemyRaceDefinition>> getEnemyRaceDefintionList(List<String> key);

}

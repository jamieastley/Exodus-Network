package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyRaceDefinition;

import java.util.List;

@Dao
public interface RaceDefinitionDAO {

    @Query("SELECT * FROM DestinyRaceDefinition")
    Maybe<DestinyRaceDefinition> getAllRaceDefinitions();

    @Query("SELECT * FROM DestinyRaceDefinition WHERE id = :key")
    Maybe<DestinyRaceDefinition> getRaceDefinition(String key);

    @Query("SELECT * FROM DestinyRaceDefinition WHERE id IN (:key)")
    Maybe<List<DestinyRaceDefinition>> getRaceDefinitionList(List<String> key);
}

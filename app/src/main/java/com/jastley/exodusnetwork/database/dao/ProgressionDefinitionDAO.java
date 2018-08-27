package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyProgressionDefinition;

import java.util.List;

@Dao
public interface ProgressionDefinitionDAO {

    @Query("SELECT * FROM DestinyProgressionDefinition")
    Maybe<DestinyProgressionDefinition> getAllProgressionDefinitions();

    @Query("SELECT * FROM DestinyProgressionDefinition WHERE id = :key")
    Maybe<DestinyProgressionDefinition> getProgressionDefinitions(String key);

    @Query("SELECT * FROM DestinyProgressionDefinition WHERE id IN (:key)")
    Maybe<List<DestinyProgressionDefinition>> getProgressionDefinitionList(List<String> key);
}

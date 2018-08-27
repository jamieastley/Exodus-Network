package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyObjectiveDefinition;

import java.util.List;

@Dao
public interface ObjectiveDefinitionDAO {

    @Query("SELECT * FROM DestinyObjectiveDefinition")
    Maybe<DestinyObjectiveDefinition> getAllObjectiveDefinitions();

    @Query("SELECT * FROM DestinyObjectiveDefinition WHERE id = :key")
    Maybe<DestinyObjectiveDefinition> getObjectiveDefinition(String key);

    @Query("SELECT * FROM DestinyObjectiveDefinition WHERE id IN (:key)")
    Maybe<List<DestinyObjectiveDefinition>> getObjectiveDefinitionList(List<String> key);
}

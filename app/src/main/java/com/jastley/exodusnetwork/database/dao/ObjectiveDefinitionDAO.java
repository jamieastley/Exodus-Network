package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyObjectiveDefinition;

import java.util.List;

@Dao
public interface ObjectiveDefinitionDAO {

    @Query("SELECT * FROM DestinyObjectiveDefinition")
    LiveData<DestinyObjectiveDefinition> getAllObjectiveDefinitions();

    @Query("SELECT * FROM DestinyObjectiveDefinition WHERE id = :key")
    LiveData<DestinyObjectiveDefinition> getObjectiveDefinition(String key);

    @Query("SELECT * FROM DestinyObjectiveDefinition WHERE id IN (:key)")
    LiveData<List<DestinyObjectiveDefinition>> getObjectiveDefinitionList(List<String> key);
}

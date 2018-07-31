package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyProgressionLevelRequirementDefinition;

import java.util.List;

@Dao
public interface ProgressionLevelRequirementDefinitionDAO {

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition")
    LiveData<DestinyProgressionLevelRequirementDefinition> getAllProgressionRequirementDefinitions();

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition WHERE id = :key")
    LiveData<DestinyProgressionLevelRequirementDefinition> getProgressionLevelRequirementDefinition(String key);

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition WHERE id IN (:key)")
    LiveData<List<DestinyProgressionLevelRequirementDefinition>> getProgressionLevelRequirementDefinitionList(List<String> key);

}

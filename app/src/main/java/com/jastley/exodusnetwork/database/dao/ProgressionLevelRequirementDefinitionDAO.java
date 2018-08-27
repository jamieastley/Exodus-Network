package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyProgressionLevelRequirementDefinition;

import java.util.List;

@Dao
public interface ProgressionLevelRequirementDefinitionDAO {

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition")
    Maybe<DestinyProgressionLevelRequirementDefinition> getAllProgressionRequirementDefinitions();

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition WHERE id = :key")
    Maybe<DestinyProgressionLevelRequirementDefinition> getProgressionLevelRequirementDefinition(String key);

    @Query("SELECT * FROM DestinyProgressionLevelRequirementDefinition WHERE id IN (:key)")
    Maybe<List<DestinyProgressionLevelRequirementDefinition>> getProgressionLevelRequirementDefinitionList(List<String> key);

}

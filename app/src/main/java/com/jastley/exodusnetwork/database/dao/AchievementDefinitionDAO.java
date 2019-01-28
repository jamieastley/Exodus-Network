package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyAchievementDefinition;

import java.util.List;

@Dao
public interface AchievementDefinitionDAO {

    @Query("SELECT * FROM DestinyAchievementDefinition")
    Maybe<List<DestinyAchievementDefinition>> getAllAchievementDefinitions();

    @Query("SELECT * FROM DestinyAchievementDefinition WHERE id = :key")
    Maybe<DestinyAchievementDefinition> getAchievementDefinition(String key);

    @Query("SELECT * FROM DestinyAchievementDefinition WHERE id IN (:key)")
    Maybe<List<DestinyAchievementDefinition>> getAchievementDefinitionList(List<String> key);

}

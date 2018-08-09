package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyAchievementDefinition;

import java.util.List;

@Dao
public interface AchievementDefinitionDAO {

    @Query("SELECT * FROM DestinyAchievementDefinition")
    LiveData<List<DestinyAchievementDefinition>> getAllAchievementDefinitions();

    @Query("SELECT * FROM DestinyAchievementDefinition WHERE id = :key")
    LiveData<DestinyAchievementDefinition> getAchievementDefinition(String key);

    @Query("SELECT * FROM DestinyAchievementDefinition WHERE id IN (:key)")
    LiveData<List<DestinyAchievementDefinition>> getAchievementDefinitionList(List<String> key);

}

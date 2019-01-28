package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityModeDefinition;

import java.util.List;

@Dao
public interface ActivityModeDefinitionDAO {

    @Query("SELECT * FROM DestinyActivityModeDefinition")
    Maybe<DestinyActivityModeDefinition> getAllActivityModeDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyActivityModeDefinition destinyActivityModeDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyActivityModeDefinition> destinyActivityModeDefinitions);

    @Query("SELECT * FROM DestinyActivityModeDefinition WHERE id = :key")
    Maybe<DestinyActivityModeDefinition> getActivityDefinition(String key);

    @Query("SELECT * FROM DestinyActivityModeDefinition WHERE id IN (:key)")
    Maybe<List<DestinyActivityModeDefinition>> getActivityDefinitionList(List<String> key);
}

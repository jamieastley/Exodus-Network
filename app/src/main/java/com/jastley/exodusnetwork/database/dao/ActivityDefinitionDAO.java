package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityDefinition;

import java.util.List;


@Dao
public interface ActivityDefinitionDAO {

    @Query("SELECT * FROM DestinyActivityDefinition")
    Maybe<List<DestinyActivityDefinition>> getAllActivityDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyActivityDefinition destinyActivityDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyActivityDefinition> destinyActivityDefinitions);

    @Query("SELECT * FROM DestinyActivityDefinition WHERE id = :statKey")
    Maybe<DestinyActivityDefinition> getActivityDefinitions(String statKey);

    @Query("SELECT * FROM DestinyActivityDefinition WHERE id IN (:statKey)")
    Maybe<List<DestinyActivityDefinition>> getActivityDefinitionList(List<String> statKey);
}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityDefinition;

import java.util.List;

@Dao
public interface ActivityDefinitionDAO {

    @Query("SELECT * FROM DestinyActivityDefinition")
    LiveData<List<DestinyActivityDefinition>> getAllActivityDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyActivityDefinition destinyActivityDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyActivityDefinition> destinyActivityDefinitions);

    @Query("SELECT * FROM DestinyActivityDefinition WHERE id = :statKey")
    LiveData<DestinyActivityDefinition> getActivityDefinitions(String statKey);

    @Query("SELECT * FROM DestinyActivityDefinition WHERE id IN (:statKey)")
    LiveData<List<DestinyActivityDefinition>> getActivityDefinitionList(List<String> statKey);
}

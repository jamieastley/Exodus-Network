package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyStatGroupDefinition;

import java.util.List;

@Dao
public interface StatGroupDefinitionDAO {

    @Query("SELECT * FROM DestinyStatGroupDefinition")
    LiveData<DestinyStatGroupDefinition> getAllStatGroupDefinitions();

    @Query("SELECT * FROM DestinyStatGroupDefinition WHERE id = :key")
    LiveData<DestinyStatGroupDefinition> getStatGroupDefinition(String key);

    @Query("SELECT * FROM DestinyStatGroupDefinition WHERE id IN (:key)")
    LiveData<List<DestinyStatGroupDefinition>> getStatGroupDefinitionList(List<String> key);
}

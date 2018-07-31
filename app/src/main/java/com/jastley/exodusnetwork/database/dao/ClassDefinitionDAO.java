package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyClassDefinition;

import java.util.List;

@Dao
public interface ClassDefinitionDAO {

    @Query("SELECT * FROM DestinyClassDefinition")
    LiveData<DestinyClassDefinition> getAllClassDefinitions();

    @Query("SELECT * FROM DestinyClassDefinition WHERE id = :key")
    LiveData<DestinyClassDefinition> getClassDefinition(String key);

    @Query("SELECT * FROM DestinyClassDefinition WHERE id IN (:key)")
    LiveData<List<DestinyClassDefinition>> getClassDefinitionList(List<String> key);
}

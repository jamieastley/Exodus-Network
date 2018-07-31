package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyPlugSetDefinition;

import java.util.List;

@Dao
public interface PlugSetDefinitionDAO {

    @Query("SELECT * FROM DestinyPlugSetDefinition")
    LiveData<DestinyPlugSetDefinition> getAllPlugSetDefinitions();

    @Query("SELECT * FROM DestinyPlugSetDefinition WHERE id = :key")
    LiveData<DestinyPlugSetDefinition> getPlugSetDefinition(String key);

    @Query("SELECT * FROM DestinyPlugSetDefinition WHERE id IN (:key)")
    LiveData<List<DestinyPlugSetDefinition>> getPlugSetDefinitionList(List<String> key);
}

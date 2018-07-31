package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySocketCategoryDefinition;

import java.util.List;

@Dao
public interface SocketCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinySocketCategoryDefinition")
    LiveData<DestinySocketCategoryDefinition> getAllSocketCategoryDefinitions();

    @Query("SELECT * FROM DestinySocketCategoryDefinition WHERE id = :key")
    LiveData<DestinySocketCategoryDefinition> getSocketCategoryDefinition(String key);

    @Query("SELECT * FROM DestinySocketCategoryDefinition WHERE id IN (:key)")
    LiveData<List<DestinySocketCategoryDefinition>> getSocketCategoryDefinitionList(List<String> key);
}

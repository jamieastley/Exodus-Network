package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySocketTypeDefinition;

import java.util.List;

@Dao
public interface SocketTypeDefinitionDAO {

    @Query("SELECT * FROM DestinySocketTypeDefinition")
    LiveData<DestinySocketTypeDefinition> getAllSocketTypeDefinitions();

    @Query("SELECT * FROM DestinySocketTypeDefinition WHERE id = :key")
    LiveData<DestinySocketTypeDefinition> getSocketTypeDefinition(String key);

    @Query("SELECT * FROM DestinySocketTypeDefinition WHERE id IN (:key)")
    LiveData<List<DestinySocketTypeDefinition>> getSocketTypeDefinitionList(List<String> key);

}

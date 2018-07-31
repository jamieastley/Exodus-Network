package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyDestinationDefinition;

import java.util.List;

@Dao
public interface DestinationDefinitionDAO {

    @Query("SELECT * FROM DestinyDestinationDefinition")
    LiveData<DestinyDestinationDefinition> getDestinationDefinitions();

    @Query("SELECT * FROM DestinyDestinationDefinition WHERE id = :key")
    LiveData<DestinyDestinationDefinition> getDestinationDefinition(String key);

    @Query("SELECT * FROM DestinyDestinationDefinition WHERE id IN (:key)")
    LiveData<List<DestinyDestinationDefinition>> getDestinationDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyPlaceDefinition;

import java.util.List;

@Dao
public interface PlaceDefinitionDAO {

    @Query("SELECT * FROM DestinyPlaceDefinition")
    LiveData<DestinyPlaceDefinition> getAllPlaceDefinitions();

    @Query("SELECT * FROM DestinyPlaceDefinition WHERE id = :key")
    LiveData<DestinyPlaceDefinition> getPlaceDefinition(String key);

    @Query("SELECT * FROM DestinyPlaceDefinition WHERE id IN (:key)")
    LiveData<List<DestinyPlaceDefinition>> getPlaceDefinitionList(List<String> key);
}

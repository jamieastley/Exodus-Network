package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyGenderDefinition;

import java.util.List;

@Dao
public interface GenderDefinitionDAO {

    @Query("SELECT * FROM DestinyGenderDefinition")
    LiveData<DestinyGenderDefinition> getGenderDefinitions();

    @Query("SELECT * FROM DestinyGenderDefinition WHERE id = :key")
    LiveData<DestinyGenderDefinition> getGenderDefinition(String key);

    @Query("SELECT * FROM DestinyGenderDefinition WHERE id IN (:key)")
    LiveData<List<DestinyGenderDefinition>> getGenderDefinitionList(List<String> key);

}

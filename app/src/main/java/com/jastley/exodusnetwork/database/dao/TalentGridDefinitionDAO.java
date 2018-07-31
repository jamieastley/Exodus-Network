package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyTalentGridDefinition;

import java.util.List;

@Dao
public interface TalentGridDefinitionDAO {

    @Query("SELECT * FROM DestinyTalentGridDefinition")
    LiveData<DestinyTalentGridDefinition> getAllTalentGridDefinitions();

    @Query("SELECT * FROM DestinyTalentGridDefinition WHERE id = :key")
    LiveData<DestinyTalentGridDefinition> getTalentGridDefinition(String key);

    @Query("SELECT * FROM DestinyTalentGridDefinition WHERE id IN (:key)")
    LiveData<List<DestinyTalentGridDefinition>> getTalentGridDefinitionList(List<String> key);

}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyMaterialRequirementSetDefinition;

import java.util.List;

@Dao
public interface MaterialRequirementSetDefinitionDAO {

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition")
    LiveData<DestinyMaterialRequirementSetDefinition> getAllMaterialRequirementSetDefinitions();

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition WHERE id = :key")
    LiveData<DestinyMaterialRequirementSetDefinition> getMaterialRequirementSetDefinition(String key);

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition WHERE id IN (:key)")
    LiveData<List<DestinyMaterialRequirementSetDefinition>> getMaterialRequirementSetDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyMaterialRequirementSetDefinition;

import java.util.List;

@Dao
public interface MaterialRequirementSetDefinitionDAO {

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition")
    Maybe<DestinyMaterialRequirementSetDefinition> getAllMaterialRequirementSetDefinitions();

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition WHERE id = :key")
    Maybe<DestinyMaterialRequirementSetDefinition> getMaterialRequirementSetDefinition(String key);

    @Query("SELECT * FROM DestinyMaterialRequirementSetDefinition WHERE id IN (:key)")
    Maybe<List<DestinyMaterialRequirementSetDefinition>> getMaterialRequirementSetDefinitionList(List<String> key);
}

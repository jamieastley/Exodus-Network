package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyLocationDefinition;

import java.util.List;

@Dao
public interface LocationDefinitionDAO {

    @Query("SELECT * FROM DestinyLocationDefinition")
    Maybe<DestinyLocationDefinition> getAllLocationDefinitions();

    @Query("SELECT * FROM DestinyLocationDefinition WHERE id = :key")
    Maybe<DestinyLocationDefinition> getLocationDefinition(String key);

    @Query("SELECT * FROM DestinyLocationDefinition WHERE id IN (:key)")
    Maybe<List<DestinyLocationDefinition>> getLocationDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyClassDefinition;

import java.util.List;

@Dao
public interface ClassDefinitionDAO {

    @Query("SELECT * FROM DestinyClassDefinition")
    Maybe<DestinyClassDefinition> getAllClassDefinitions();

    @Query("SELECT * FROM DestinyClassDefinition WHERE id = :key")
    Maybe<DestinyClassDefinition> getClassDefinition(String key);

    @Query("SELECT * FROM DestinyClassDefinition WHERE id IN (:key)")
    Maybe<List<DestinyClassDefinition>> getClassDefinitionList(List<String> key);
}

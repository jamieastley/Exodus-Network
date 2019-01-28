package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyLoreDefinition;

import java.util.List;

@Dao
public interface LoreDefinitionDAO {

    @Query("SELECT * FROM DestinyLoreDefinition")
    Maybe<DestinyLoreDefinition> getAllLoreDefinitions();

    @Query("SELECT * FROM DestinyLoreDefinition WHERE id = :key")
    Maybe<DestinyLoreDefinition> getLoreDefinition(String key);

    @Query("SELECT * FROM DestinyLoreDefinition WHERE id IN (:key)")
    Maybe<List<DestinyLoreDefinition>> getLoreDefinitionList(List<String> key);
}

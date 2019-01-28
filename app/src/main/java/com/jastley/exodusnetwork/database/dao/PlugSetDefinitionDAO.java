package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyPlugSetDefinition;

import java.util.List;

@Dao
public interface PlugSetDefinitionDAO {

    @Query("SELECT * FROM DestinyPlugSetDefinition")
    Maybe<DestinyPlugSetDefinition> getAllPlugSetDefinitions();

    @Query("SELECT * FROM DestinyPlugSetDefinition WHERE id = :key")
    Maybe<DestinyPlugSetDefinition> getPlugSetDefinition(String key);

    @Query("SELECT * FROM DestinyPlugSetDefinition WHERE id IN (:key)")
    Maybe<List<DestinyPlugSetDefinition>> getPlugSetDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyDestinationDefinition;

import java.util.List;

@Dao
public interface DestinationDefinitionDAO {

    @Query("SELECT * FROM DestinyDestinationDefinition")
    Maybe<DestinyDestinationDefinition> getDestinationDefinitions();

    @Query("SELECT * FROM DestinyDestinationDefinition WHERE id = :key")
    Maybe<DestinyDestinationDefinition> getDestinationDefinition(String key);

    @Query("SELECT * FROM DestinyDestinationDefinition WHERE id IN (:key)")
    Maybe<List<DestinyDestinationDefinition>> getDestinationDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyPlaceDefinition;

import java.util.List;

@Dao
public interface PlaceDefinitionDAO {

    @Query("SELECT * FROM DestinyPlaceDefinition")
    Maybe<DestinyPlaceDefinition> getAllPlaceDefinitions();

    @Query("SELECT * FROM DestinyPlaceDefinition WHERE id = :key")
    Maybe<DestinyPlaceDefinition> getPlaceDefinition(String key);

    @Query("SELECT * FROM DestinyPlaceDefinition WHERE id IN (:key)")
    Maybe<List<DestinyPlaceDefinition>> getPlaceDefinitionList(List<String> key);
}

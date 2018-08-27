package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyStatGroupDefinition;

import java.util.List;

@Dao
public interface StatGroupDefinitionDAO {

    @Query("SELECT * FROM DestinyStatGroupDefinition")
    Maybe<DestinyStatGroupDefinition> getAllStatGroupDefinitions();

    @Query("SELECT * FROM DestinyStatGroupDefinition WHERE id = :key")
    Maybe<DestinyStatGroupDefinition> getStatGroupDefinition(String key);

    @Query("SELECT * FROM DestinyStatGroupDefinition WHERE id IN (:key)")
    Maybe<List<DestinyStatGroupDefinition>> getStatGroupDefinitionList(List<String> key);
}

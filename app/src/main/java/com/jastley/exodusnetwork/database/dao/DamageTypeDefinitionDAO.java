package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyDamageTypeDefinition;

import java.util.List;

@Dao
public interface DamageTypeDefinitionDAO {

    @Query("SELECT * FROM DestinyDamageTypeDefinition")
    Maybe<DestinyDamageTypeDefinition> getAllDamageTypeDefinitions();

    @Query("SELECT * FROM DestinyDamageTypeDefinition WHERE id = :key")
    Maybe<DestinyDamageTypeDefinition> getDamageTypeDefinition(String key);

    @Query("SELECT * FROM DestinyDamageTypeDefinition WHERE id IN (:key)")
    Maybe<List<DestinyDamageTypeDefinition>> getDamageTypeDefinitionList(List<String> key);
}

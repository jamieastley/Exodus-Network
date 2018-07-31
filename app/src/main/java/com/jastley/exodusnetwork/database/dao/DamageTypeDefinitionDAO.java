package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyDamageTypeDefinition;

import java.util.List;

@Dao
public interface DamageTypeDefinitionDAO {

    @Query("SELECT * FROM DestinyDamageTypeDefinition")
    LiveData<DestinyDamageTypeDefinition> getAllDamageTypeDefinitions();

    @Query("SELECT * FROM DestinyDamageTypeDefinition WHERE id = :key")
    LiveData<DestinyDamageTypeDefinition> getDamageTypeDefinition(String key);

    @Query("SELECT * FROM DestinyDamageTypeDefinition WHERE id IN (:key)")
    LiveData<List<DestinyDamageTypeDefinition>> getDamageTypeDefinitionList(List<String> key);
}

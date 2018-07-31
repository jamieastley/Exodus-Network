package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyItemTierTypeDefinition;

import java.util.List;

@Dao
public interface ItemTierTypeDefinitionDAO {

    @Query("SELECT * FROM DestinyItemTierTypeDefinition")
    LiveData<DestinyItemTierTypeDefinition> getAllItemTierTypeDefinitions();

    @Query("SELECT * FROM DestinyItemTierTypeDefinition WHERE id = :key")
    LiveData<DestinyItemTierTypeDefinition> getItemTierTypeDefinition(String key);

    @Query("SELECT * FROM DestinyItemTierTypeDefinition WHERE id IN (:key)")
    LiveData<List<DestinyItemTierTypeDefinition>> getItemTierTypedefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyItemTierTypeDefinition;

import java.util.List;

@Dao
public interface ItemTierTypeDefinitionDAO {

    @Query("SELECT * FROM DestinyItemTierTypeDefinition")
    Maybe<DestinyItemTierTypeDefinition> getAllItemTierTypeDefinitions();

    @Query("SELECT * FROM DestinyItemTierTypeDefinition WHERE id = :key")
    Maybe<DestinyItemTierTypeDefinition> getItemTierTypeDefinition(String key);

    @Query("SELECT * FROM DestinyItemTierTypeDefinition WHERE id IN (:key)")
    Maybe<List<DestinyItemTierTypeDefinition>> getItemTierTypedefinitionList(List<String> key);
}

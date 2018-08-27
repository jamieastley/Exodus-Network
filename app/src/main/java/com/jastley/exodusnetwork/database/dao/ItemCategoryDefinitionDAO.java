package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyItemCategoryDefinition;

import java.util.List;

@Dao
public interface ItemCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinyItemCategoryDefinition")
    Maybe<DestinyItemCategoryDefinition> getItemCategoryDefinitions();

    @Query("SELECT * FROM DestinyItemCategoryDefinition WHERE id = :key")
    Maybe<DestinyItemCategoryDefinition> getItemCategoryDefinition(String key);

    @Query("SELECT * FROM DestinyItemCategoryDefinition WHERE id IN (:key)")
    Maybe<List<DestinyItemCategoryDefinition>> getItemCategoryDefinitionList(List<String> key);
}

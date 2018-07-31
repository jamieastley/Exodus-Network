package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyItemCategoryDefinition;

import java.util.List;

@Dao
public interface ItemCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinyItemCategoryDefinition")
    LiveData<DestinyItemCategoryDefinition> getItemCategoryDefinitions();

    @Query("SELECT * FROM DestinyItemCategoryDefinition WHERE id = :key")
    LiveData<DestinyItemCategoryDefinition> getItemCategoryDefinition(String key);

    @Query("SELECT * FROM DestinyItemCategoryDefinition WHERE id IN (:key)")
    LiveData<List<DestinyItemCategoryDefinition>> getItemCategoryDefinitionList(List<String> key);
}

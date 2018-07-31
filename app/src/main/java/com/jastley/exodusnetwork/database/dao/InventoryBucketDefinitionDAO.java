package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyInventoryBucketDefinition;

import java.util.List;

@Dao
public interface InventoryBucketDefinitionDAO {

    @Query("SELECT * FROM DestinyInventoryBucketDefinition")
    LiveData<DestinyInventoryBucketDefinition> getInventoryBucketDefinitions();

    @Query("SELECT * FROM DestinyInventoryBucketDefinition WHERE id = :key")
    LiveData<DestinyInventoryBucketDefinition> getInventoryBucketDefintion(String key);

    @Query("SELECT * FROM DestinyInventoryBucketDefinition WHERE id IN (:key)")
    LiveData<List<DestinyInventoryBucketDefinition>> getInventoryBucketDefinitionList(List<String> key);

}

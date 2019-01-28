package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyInventoryBucketDefinition;

import java.util.List;

@Dao
public interface InventoryBucketDefinitionDAO {

    @Query("SELECT * FROM DestinyInventoryBucketDefinition")
    Maybe<DestinyInventoryBucketDefinition> getInventoryBucketDefinitions();

    @Query("SELECT * FROM DestinyInventoryBucketDefinition WHERE id = :key")
    Maybe<DestinyInventoryBucketDefinition> getInventoryBucketDefintion(String key);

    @Query("SELECT * FROM DestinyInventoryBucketDefinition WHERE id IN (:key)")
    Maybe<List<DestinyInventoryBucketDefinition>> getInventoryBucketDefinitionList(List<String> key);

}

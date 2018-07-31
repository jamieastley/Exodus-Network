package com.jastley.exodusnetwork.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by jamie on 9/4/18.
 */

@Dao
public interface InventoryItemDefinitionDAO {

    @Query("SELECT * FROM DestinyInventoryItemDefinition")
    Maybe<List<DestinyInventoryItemDefinition>> getAllCollectables();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyInventoryItemDefinition destinyInventoryItemDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyInventoryItemDefinition> collectables);

    @Query("SELECT * FROM DestinyInventoryItemDefinition WHERE id = :itemKey")
    Maybe<DestinyInventoryItemDefinition> getItemByKey(String itemKey);

    @Query("SELECT * FROM DestinyInventoryItemDefinition WHERE id IN (:itemKey)")
    Maybe<List<DestinyInventoryItemDefinition>> getItemsListByKey(List<String> itemKey);

    @Update
    void update(DestinyInventoryItemDefinition destinyInventoryItemDefinitionRow);
}

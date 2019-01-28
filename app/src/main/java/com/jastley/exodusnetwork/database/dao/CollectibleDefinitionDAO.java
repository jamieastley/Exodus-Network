package com.jastley.exodusnetwork.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyCollectibleDefinition;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CollectibleDefinitionDAO {

    @Query("SELECT * FROM DestinyCollectibleDefinition")
    Maybe<List<DestinyCollectibleDefinition>> getAllCollectibles();

    @Query("SELECT * FROM DestinyCollectibleDefinition WHERE id = :key")
    Maybe<DestinyCollectibleDefinition> getItemByKey(String key);

    @Query("SELECT * FROM DestinyCollectibleDefinition WHERE id IN (:keyList)")
    Maybe<List<DestinyCollectibleDefinition>> getItemListByKey(List<String> keyList);
}

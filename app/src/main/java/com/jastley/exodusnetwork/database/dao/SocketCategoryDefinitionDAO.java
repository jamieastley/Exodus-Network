package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySocketCategoryDefinition;

import java.util.List;

@Dao
public interface SocketCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinySocketCategoryDefinition")
    Maybe<DestinySocketCategoryDefinition> getAllSocketCategoryDefinitions();

    @Query("SELECT * FROM DestinySocketCategoryDefinition WHERE id = :key")
    Maybe<DestinySocketCategoryDefinition> getSocketCategoryDefinition(String key);

    @Query("SELECT * FROM DestinySocketCategoryDefinition WHERE id IN (:key)")
    Maybe<List<DestinySocketCategoryDefinition>> getSocketCategoryDefinitionList(List<String> key);
}

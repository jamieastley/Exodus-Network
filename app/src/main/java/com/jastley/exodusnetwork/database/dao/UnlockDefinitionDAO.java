package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyUnlockDefinition;

import java.util.List;

@Dao
public interface UnlockDefinitionDAO {

    @Query("SELECT * FROM DestinyUnlockDefinition")
    Maybe<DestinyUnlockDefinition> getAllUnlockDefinitions();

    @Query("SELECT * FROM DestinyUnlockDefinition WHERE id = :key")
    Maybe<DestinyUnlockDefinition> getUnlockDefinition(String key);

    @Query("SELECT * FROM DestinyUnlockDefinition WHERE id IN (:key)")
    Maybe<List<DestinyUnlockDefinition>> getUnlockDefinitionList(List<String> key);

}

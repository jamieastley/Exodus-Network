package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

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

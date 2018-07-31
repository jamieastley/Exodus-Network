package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyUnlockDefinition;

import java.util.List;

@Dao
public interface UnlockDefinitionDAO {

    @Query("SELECT * FROM DestinyUnlockDefinition")
    LiveData<DestinyUnlockDefinition> getAllUnlockDefinitions();

    @Query("SELECT * FROM DestinyUnlockDefinition WHERE id = :key")
    LiveData<DestinyUnlockDefinition> getUnlockDefinition(String key);

    @Query("SELECT * FROM DestinyUnlockDefinition WHERE id IN (:key)")
    LiveData<List<DestinyUnlockDefinition>> getUnlockDefinitionList(List<String> key);

}

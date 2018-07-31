package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyBondDefinition;

import java.util.List;

@Dao
public interface BondDefinitionDAO {

    @Query("SELECT * FROM DestinyBondDefinition")
    LiveData<DestinyBondDefinition> getAllBondDefinitions();

    @Query("SELECT * FROM DestinyBondDefinition WHERE id = :key")
    LiveData<DestinyBondDefinition> getBondDefinition(String key);

    @Query("SELECT * FROM DestinyBondDefinition WHERE id in (:key)")
    LiveData<List<DestinyBondDefinition>> getBondDefinitionLIst(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyBondDefinition;

import java.util.List;

@Dao
public interface BondDefinitionDAO {

    @Query("SELECT * FROM DestinyBondDefinition")
    Maybe<DestinyBondDefinition> getAllBondDefinitions();

    @Query("SELECT * FROM DestinyBondDefinition WHERE id = :key")
    Maybe<DestinyBondDefinition> getBondDefinition(String key);

    @Query("SELECT * FROM DestinyBondDefinition WHERE id in (:key)")
    Maybe<List<DestinyBondDefinition>> getBondDefinitionLIst(List<String> key);
}

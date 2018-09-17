package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySocketTypeDefinition;

import java.util.List;

@Dao
public interface SocketTypeDefinitionDAO {

    @Query("SELECT * FROM DestinySocketTypeDefinition")
    Maybe<DestinySocketTypeDefinition> getAllSocketTypeDefinitions();

    @Query("SELECT * FROM DestinySocketTypeDefinition WHERE id = :key")
    Maybe<DestinySocketTypeDefinition> getSocketTypeDefinition(String key);

    @Query("SELECT * FROM DestinySocketTypeDefinition WHERE id IN (:key)")
    Maybe<List<DestinySocketTypeDefinition>> getSocketTypeDefinitionList(List<String> key);

}

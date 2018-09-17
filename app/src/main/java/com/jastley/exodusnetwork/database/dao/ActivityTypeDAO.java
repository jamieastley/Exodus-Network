package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityTypeDefinition;

import java.util.List;

@Dao
public interface ActivityTypeDAO {

    @Query("SELECT * FROM DestinyActivityTypeDefinition")
    Maybe<List<DestinyActivityTypeDefinition>> getAllActivityTypeDefinitions();

    @Query("SELECT * FROM DestinyActivityTypeDefinition WHERE id = :key")
    Maybe<DestinyActivityTypeDefinition> getActivityTypeDefinition(String key);

    @Query("SELECT * FROM DestinyActivityTypeDefinition WHERE id in (:key)")
    Maybe<List<DestinyActivityTypeDefinition>> getActivityTypeDefinition(List<String> key);

}

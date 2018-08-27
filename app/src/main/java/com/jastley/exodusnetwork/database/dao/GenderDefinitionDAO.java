package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyGenderDefinition;

import java.util.List;

@Dao
public interface GenderDefinitionDAO {

    @Query("SELECT * FROM DestinyGenderDefinition")
    Maybe<DestinyGenderDefinition> getGenderDefinitions();

    @Query("SELECT * FROM DestinyGenderDefinition WHERE id = :key")
    Maybe<DestinyGenderDefinition> getGenderDefinition(String key);

    @Query("SELECT * FROM DestinyGenderDefinition WHERE id IN (:key)")
    Maybe<List<DestinyGenderDefinition>> getGenderDefinitionList(List<String> key);

}

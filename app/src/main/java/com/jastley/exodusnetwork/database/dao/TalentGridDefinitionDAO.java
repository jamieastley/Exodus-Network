package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyTalentGridDefinition;

import java.util.List;

@Dao
public interface TalentGridDefinitionDAO {

    @Query("SELECT * FROM DestinyTalentGridDefinition")
    Maybe<DestinyTalentGridDefinition> getAllTalentGridDefinitions();

    @Query("SELECT * FROM DestinyTalentGridDefinition WHERE id = :key")
    Maybe<DestinyTalentGridDefinition> getTalentGridDefinition(String key);

    @Query("SELECT * FROM DestinyTalentGridDefinition WHERE id IN (:key)")
    Maybe<List<DestinyTalentGridDefinition>> getTalentGridDefinitionList(List<String> key);

}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyMedalTierDefinition;

import java.util.List;

@Dao
public interface MedalTierDefinitionDAO {

    @Query("SELECT * FROM DestinyMedalTierDefinition")
    Maybe<DestinyMedalTierDefinition> getAllMedalTierDefinitions();

    @Query("SELECT * FROM DestinyMedalTierDefinition WHERE id = :key")
    Maybe<DestinyMedalTierDefinition> getMedalTierDefinition(String key);

    @Query("SELECT * FROM DestinyMedalTierDefinition WHERE id IN (:key)")
    Maybe<List<DestinyMedalTierDefinition>> getMedalTierDefinitionList(List<String> key);
}

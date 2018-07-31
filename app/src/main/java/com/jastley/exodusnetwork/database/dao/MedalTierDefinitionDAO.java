package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyMedalTierDefinition;

import java.util.List;

@Dao
public interface MedalTierDefinitionDAO {

    @Query("SELECT * FROM DestinyMedalTierDefinition")
    LiveData<DestinyMedalTierDefinition> getAllMedalTierDefinitions();

    @Query("SELECT * FROM DestinyMedalTierDefinition WHERE id = :key")
    LiveData<DestinyMedalTierDefinition> getMedalTierDefinition(String key);

    @Query("SELECT * FROM DestinyMedalTierDefinition WHERE id IN (:key)")
    LiveData<List<DestinyMedalTierDefinition>> getMedalTierDefinitionList(List<String> key);
}

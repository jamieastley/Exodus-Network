package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyRewardSourceDefinition;

import java.util.List;

@Dao
public interface RewardSourceDefinitionDAO {

    @Query("SELECT * FROM DestinyRewardSourceDefinition")
    LiveData<DestinyRewardSourceDefinition> getAllRewardsSourceDefinitions();

    @Query("SELECT * FROM DestinyRewardSourceDefinition WHERE id = :key")
    LiveData<DestinyRewardSourceDefinition> getRewardSourceDefinition(String key);

    @Query("SELECT * FROM DestinyRewardSourceDefinition WHERE id IN (:key)")
    LiveData<List<DestinyRewardSourceDefinition>> getRewardSourceDefinitionList(List<String> key);
}

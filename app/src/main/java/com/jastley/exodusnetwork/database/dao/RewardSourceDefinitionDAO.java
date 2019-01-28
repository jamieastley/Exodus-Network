package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyRewardSourceDefinition;

import java.util.List;

@Dao
public interface RewardSourceDefinitionDAO {

    @Query("SELECT * FROM DestinyRewardSourceDefinition")
    Maybe<DestinyRewardSourceDefinition> getAllRewardsSourceDefinitions();

    @Query("SELECT * FROM DestinyRewardSourceDefinition WHERE id = :key")
    Maybe<DestinyRewardSourceDefinition> getRewardSourceDefinition(String key);

    @Query("SELECT * FROM DestinyRewardSourceDefinition WHERE id IN (:key)")
    Maybe<List<DestinyRewardSourceDefinition>> getRewardSourceDefinitionList(List<String> key);
}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyHistoricalStatsDefinition;

import java.util.List;

@Dao
public interface HistoricalStatsDefinitionDAO {

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition")
    Maybe<DestinyHistoricalStatsDefinition> getHistoricalStatDefinitions();

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition WHERE id = :key")
    Maybe<DestinyHistoricalStatsDefinition> getHistoricalStatDefinition(String key);

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition WHERE id IN (:key)")
    Maybe<List<DestinyHistoricalStatsDefinition>> getHistoricalDefinitionList(List<String> key);
}

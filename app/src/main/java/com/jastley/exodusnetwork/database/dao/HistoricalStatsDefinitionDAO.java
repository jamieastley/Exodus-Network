package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyHistoricalStatsDefinition;

import java.util.List;

@Dao
public interface HistoricalStatsDefinitionDAO {

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition")
    LiveData<DestinyHistoricalStatsDefinition> getHistoricalStatDefinitions();

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition WHERE id = :key")
    LiveData<DestinyHistoricalStatsDefinition> getHistoricalStatDefinition(String key);

    @Query("SELECT * FROM DestinyHistoricalStatsDefinition WHERE id IN (:key)")
    LiveData<List<DestinyHistoricalStatsDefinition>> getHistoricalDefinitionList(List<String> key);
}

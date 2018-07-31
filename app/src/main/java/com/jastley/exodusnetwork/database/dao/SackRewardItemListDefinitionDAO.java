package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySackRewardItemListDefinition;

import java.util.List;

@Dao
public interface SackRewardItemListDefinitionDAO {

    @Query("SELECT * FROM DestinySackRewardItemListDefinition")
    LiveData<DestinySackRewardItemListDefinition> getAllSackRewardDefinitions();

    @Query("SELECT * FROM DestinySackRewardItemListDefinition WHERE id = :key")
    LiveData<DestinySackRewardItemListDefinition> getSackRewardDefinition(String key);

    @Query("SELECT * FROM DestinySackRewardItemListDefinition WHERE id IN (:key)")
    LiveData<List<DestinySackRewardItemListDefinition>> getSackRewardDefinitionList(List<String> key);
}

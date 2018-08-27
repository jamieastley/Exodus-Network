package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySackRewardItemListDefinition;

import java.util.List;

@Dao
public interface SackRewardItemListDefinitionDAO {

    @Query("SELECT * FROM DestinySackRewardItemListDefinition")
    Maybe<DestinySackRewardItemListDefinition> getAllSackRewardDefinitions();

    @Query("SELECT * FROM DestinySackRewardItemListDefinition WHERE id = :key")
    Maybe<DestinySackRewardItemListDefinition> getSackRewardDefinition(String key);

    @Query("SELECT * FROM DestinySackRewardItemListDefinition WHERE id IN (:key)")
    Maybe<List<DestinySackRewardItemListDefinition>> getSackRewardDefinitionList(List<String> key);
}

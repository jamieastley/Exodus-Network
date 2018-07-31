package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyStatDefinition;

import java.util.List;

@Dao
public interface StatDefinitionDAO {

    @Query("SELECT * FROM DestinyStatDefinition")
    LiveData<List<DestinyStatDefinition>> getAllStatDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyStatDefinition destinyStatDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyStatDefinition> destinyStatDefinition);

    @Query("SELECT * FROM DestinyStatDefinition WHERE id = :statKey")
    LiveData<DestinyStatDefinition> getStatDefinitionByKey(String statKey);

    @Query("SELECT * FROM DestinyStatDefinition WHERE id IN (:statKey)")
    LiveData<List<DestinyStatDefinition>> getStatDefinitionList(List<String> statKey);
}

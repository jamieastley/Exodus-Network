package com.jastley.exodusnetwork.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyStatDefinition;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface StatDefinitionDAO {

    @Query("SELECT * FROM DestinyStatDefinition")
    Maybe<List<DestinyStatDefinition>> getAllStatDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyStatDefinition destinyStatDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyStatDefinition> destinyStatDefinition);

    @Query("SELECT * FROM DestinyStatDefinition WHERE id = :statKey")
    Maybe<DestinyStatDefinition> getStatDefinitionByKey(String statKey);

    @Query("SELECT * FROM DestinyStatDefinition WHERE id IN (:statKey)")
    Maybe<List<DestinyStatDefinition>> getStatDefinitionList(List<String> statKey);
}

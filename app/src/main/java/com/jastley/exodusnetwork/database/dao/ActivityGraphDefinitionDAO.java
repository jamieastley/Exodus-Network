package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityGraphDefinition;

import java.util.List;

@Dao
public interface ActivityGraphDefinitionDAO {

    @Query("SELECT * FROM DestinyActivityGraphDefinition")
    Maybe<List<DestinyActivityGraphDefinition>> getAllActivityGrapDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyActivityGraphDefinition destinyActivityGraphDefinition );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyActivityGraphDefinition> destinyActivityGraphDefinitions);

    @Query("SELECT * FROM DestinyActivityGraphDefinition WHERE id = :statKey")
    Maybe<DestinyActivityGraphDefinition> getActivityGraph(String statKey);

    @Query("SELECT * FROM DestinyActivityGraphDefinition WHERE id IN (:statKey)")
    Maybe<List<DestinyActivityGraphDefinition>> getActivityGraphList(List<String> statKey);
}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityGraphDefinition;

import java.util.List;

@Dao
public interface ActivityGraphDefinitionDAO {

    @Query("SELECT * FROM DestinyActivityGraphDefinition")
    LiveData<List<DestinyActivityGraphDefinition>> getAllActivityGrapDefinitions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyActivityGraphDefinition destinyActivityGraphDefinition );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyActivityGraphDefinition> destinyActivityGraphDefinitions);

    @Query("SELECT * FROM DestinyActivityGraphDefinition WHERE id = :statKey")
    LiveData<DestinyActivityGraphDefinition> getActivityGraph(String statKey);

    @Query("SELECT * FROM DestinyActivityGraphDefinition WHERE id IN (:statKey)")
    LiveData<List<DestinyActivityGraphDefinition>> getActivityGraphList(List<String> statKey);
}

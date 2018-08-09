package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyChecklistDefinition;

import java.util.List;

@Dao
public interface ChecklistDefinitionDAO {

    @Query("SELECT * FROM DestinyChecklistDefinition")
    LiveData<List<DestinyChecklistDefinition>> getAllChecklistDefinitions();

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id = :key")
    LiveData<DestinyChecklistDefinition> getChecklistDefinition(String key);

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id IN (:key)")
    LiveData<List<DestinyChecklistDefinition>> getChecklistDefinitionList(List<String> key);
}

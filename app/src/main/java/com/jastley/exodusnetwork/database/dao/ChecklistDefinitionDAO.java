package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyChecklistDefinition;

import java.util.List;

@Dao
public interface ChecklistDefinitionDAO {

    @Query("SELECT * FROM DestinyChecklistDefinition")
    Maybe<List<DestinyChecklistDefinition>> getAllChecklistDefinitions();

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id = :key")
    Maybe<DestinyChecklistDefinition> getChecklistDefinition(String key);

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id IN (:key)")
    Maybe<List<DestinyChecklistDefinition>> getChecklistDefinitionList(List<String> key);
}

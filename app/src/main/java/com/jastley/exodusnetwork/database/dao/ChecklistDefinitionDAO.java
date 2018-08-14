package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyChecklistDefinition;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ChecklistDefinitionDAO {

    @Query("SELECT * FROM DestinyChecklistDefinition")
    Maybe<List<DestinyChecklistDefinition>> getAllChecklistDefinitions();

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id = :key")
    Maybe<DestinyChecklistDefinition> getChecklistDefinition(String key);

    @Query("SELECT * FROM DestinyChecklistDefinition WHERE id IN (:key)")
    Maybe<List<DestinyChecklistDefinition>> getChecklistDefinitionList(List<String> key);
}

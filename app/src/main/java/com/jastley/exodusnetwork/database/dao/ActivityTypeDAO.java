package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityTypeDefinition;

import java.util.List;

@Dao
public interface ActivityTypeDAO {

    @Query("SELECT * FROM DestinyActivityTypeDefinition")
    LiveData<List<DestinyActivityTypeDefinition>> getAllActivityTypeDefinitions();

    @Query("SELECT * FROM DestinyActivityTypeDefinition WHERE id = :key")
    LiveData<DestinyActivityTypeDefinition> getActivityTypeDefinition(String key);

    @Query("SELECT * FROM DestinyActivityTypeDefinition WHERE id in (:key)")
    LiveData<List<DestinyActivityTypeDefinition>> getActivityTypeDefinition(List<String> key);

}

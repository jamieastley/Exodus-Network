package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySandboxPerkDefinition;

import java.util.List;

@Dao
public interface SandboxPerkDefinitionDAO {

    @Query("SELECT * FROM DestinySandboxPerkDefinition")
    LiveData<DestinySandboxPerkDefinition> getAllSandboxPerkDefinitions();

    @Query("SELECT * FROM DestinySandboxPerkDefinition WHERE id = :key")
    LiveData<DestinySandboxPerkDefinition> getSandboxPerkDefinition(String key);

    @Query("SELECT * FROM DestinySandboxPerkDefinition WHERE id IN (:key)")
    LiveData<List<DestinySandboxPerkDefinition>> getSandboxPerkDefinitionList(List<String> key);
}


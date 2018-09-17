package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinySandboxPerkDefinition;

import java.util.List;

@Dao
public interface SandboxPerkDefinitionDAO {

    @Query("SELECT * FROM DestinySandboxPerkDefinition")
    Maybe<DestinySandboxPerkDefinition> getAllSandboxPerkDefinitions();

    @Query("SELECT * FROM DestinySandboxPerkDefinition WHERE id = :key")
    Maybe<DestinySandboxPerkDefinition> getSandboxPerkDefinition(String key);

    @Query("SELECT * FROM DestinySandboxPerkDefinition WHERE id IN (:key)")
    Maybe<List<DestinySandboxPerkDefinition>> getSandboxPerkDefinitionList(List<String> key);
}


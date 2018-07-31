package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityModifierDefinition;

import java.util.List;

@Dao
public interface ActivityModifierDAO {

    @Query("SELECT * FROM DestinyActivityModifierDefinition")
    LiveData<DestinyActivityModifierDefinition> getAllActivityModifiers();

    @Query("SELECT * FROM DestinyActivityModifierDefinition WHERE id = :key")
    LiveData<DestinyActivityModifierDefinition> getActivityModifier(String key);

    @Query("SELECT * FROM DestinyActivityModifierDefinition WHERE id IN (:key)")
    LiveData<List<DestinyActivityModifierDefinition>> getActivityModifierList(List<String> key);
}

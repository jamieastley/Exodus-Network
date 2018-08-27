package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyActivityModifierDefinition;

import java.util.List;

@Dao
public interface ActivityModifierDAO {

    @Query("SELECT * FROM DestinyActivityModifierDefinition")
    Maybe<DestinyActivityModifierDefinition> getAllActivityModifiers();

    @Query("SELECT * FROM DestinyActivityModifierDefinition WHERE id = :key")
    Maybe<DestinyActivityModifierDefinition> getActivityModifier(String key);

    @Query("SELECT * FROM DestinyActivityModifierDefinition WHERE id IN (:key)")
    Maybe<List<DestinyActivityModifierDefinition>> getActivityModifierList(List<String> key);
}

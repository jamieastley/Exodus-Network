package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyEquipmentSlotDefinition;

import java.util.List;

@Dao
public interface EquipmentSlotDefinitionDAO {

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition")
    Maybe<DestinyEquipmentSlotDefinition> getEquipmentSlotDefinitions();

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition WHERE id = :key")
    Maybe<DestinyEquipmentSlotDefinition> getEquipmentSlotDefinition(String key);

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition WHERE id IN (:key)")
    Maybe<List<DestinyEquipmentSlotDefinition>> getEquipmentSlotDefinitionList(List<String> key);

}

package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyEquipmentSlotDefinition;

import java.util.List;

@Dao
public interface EquipmentSlotDefinitionDAO {

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition")
    LiveData<DestinyEquipmentSlotDefinition> getEquipmentSlotDefinitions();

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition WHERE id = :key")
    LiveData<DestinyEquipmentSlotDefinition> getEquipmentSlotDefinition(String key);

    @Query("SELECT * FROM DestinyEquipmentSlotDefinition WHERE id IN (:key)")
    LiveData<List<DestinyEquipmentSlotDefinition>> getEquipmentSlotDefinitionList(List<String> key);

}

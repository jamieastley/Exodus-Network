package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Observable;
import com.jastley.warmindfordestiny2.database.models.DestinyFactionDefinition;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface FactionsDAO {

    Long test = 0xFFFFFFFFL;
//    + 4294967296

    @Query("SELECT * FROM DestinyFactionDefinition")
    Maybe<List<DestinyFactionDefinition>> getAllFactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyFactionDefinition faction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyFactionDefinition> faction);

    @Query("SELECT * FROM DestinyFactionDefinition WHERE `id` = :itemKey")
    Single<DestinyFactionDefinition> getFactionByKey(String itemKey);

//    @Query("SELECT * FROM DestinyFactionDefinition WHERE `key` = :itemKey")
//    Observable<DestinyFactionDefinition> getFactionByKeyObsv(String itemKey);

//    @Query("SELECT * FROM DestinyFactionDefinition WHERE `key` = :itemKey ")
//    Maybe<List<DestinyFactionDefinition>> getFactionsListByKey(List<String> itemKey);


    @Query("SELECT * FROM DestinyFactionDefinition WHERE id IN (:itemKey) - CAST(4294967296 as Long) OR id IN (:itemKey)")
//    @Query("SELECT CASE WHEN id < 0 THEN id + 4294967296 IN (:itemKey) OR `id` IN (:itemKey) END FROM DestinyFactionDefinition")
    Maybe<List<DestinyFactionDefinition>> getFactionsListByKey(List<Long> itemKey);
}

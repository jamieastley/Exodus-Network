package com.jastley.exodusnetwork.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.jastley.exodusnetwork.database.models.DestinyFactionDefinition;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface FactionDefinitionDAO {

    @Query("SELECT * FROM DestinyFactionDefinition")
    Maybe<List<DestinyFactionDefinition>> getAllFactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyFactionDefinition faction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyFactionDefinition> faction);

    @Query("SELECT * FROM DestinyFactionDefinition WHERE id = :itemKey OR id = :unsigned")
    Single<DestinyFactionDefinition> getFactionByKey(String itemKey, String unsigned);

//    @Query("SELECT * FROM DestinyFactionDefinition WHERE `key` = :itemKey")
//    Observable<DestinyFactionDefinition> getFactionByKeyObsv(String itemKey);

//    @Query("SELECT * FROM DestinyFactionDefinition WHERE `key` = :itemKey ")
//    Maybe<List<DestinyFactionDefinition>> getFactionsListByKey(List<String> itemKey);


    @Query("SELECT * FROM DestinyFactionDefinition WHERE id IN (:itemKey)")
    Maybe<List<DestinyFactionDefinition>> getFactionsListByKey(List<String> itemKey);
}

package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Observable;
import com.jastley.warmindfordestiny2.database.models.Factions;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface FactionsDAO {

    @Query("SELECT * FROM Factions")
    Maybe<List<Factions>> getAllFactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Factions faction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Factions> faction);

    @Query("SELECT * FROM Factions WHERE `key` = :itemKey")
    Maybe<Factions> getFactionByKey(String itemKey);

//    @Query("SELECT * FROM Factions WHERE `key` = :itemKey")
//    Observable<Factions> getFactionByKeyObsv(String itemKey);

//    @Query("SELECT * FROM Factions WHERE `key` = :itemKey ")
//    Maybe<List<Factions>> getFactionsListByKey(List<String> itemKey);


    @Query("SELECT * FROM Factions WHERE `key` IN (:itemKey) ")
    Maybe<List<Factions>> getFactionsListByKey(List<String> itemKey);
}

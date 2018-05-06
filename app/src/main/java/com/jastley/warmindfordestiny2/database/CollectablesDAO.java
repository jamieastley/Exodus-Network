package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.warmindfordestiny2.database.models.Collectables;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by jamie on 9/4/18.
 */

@Dao
public interface CollectablesDAO {

    @Query("SELECT * FROM Collectables")
    Flowable <List<Collectables>> getAllCollectables();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Collectables collectables);

    @Insert
    void insertAll(List<Collectables> collectables);

    @Query("SELECT * FROM Collectables WHERE `key` = :itemKey")
    Maybe<Collectables> getItemByKey(String itemKey);

    @Update
    void update(Collectables collectablesRow);
}

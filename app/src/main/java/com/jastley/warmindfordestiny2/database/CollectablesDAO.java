package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.warmindfordestiny2.database.models.Collectables;

/**
 * Created by jamie on 9/4/18.
 */

@Dao
public interface CollectablesDAO {

    @Query("SELECT * FROM Collectables")
    Collectables getAllCollectables();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Collectables collectables);

    @Update
    void update(Collectables collectablesRow);
}

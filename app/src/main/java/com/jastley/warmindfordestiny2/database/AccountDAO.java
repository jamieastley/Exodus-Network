package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.warmindfordestiny2.database.models.Account;

/**
 * Created by jamie on 9/4/18.
 */

@Dao
public interface AccountDAO {

    @Query("SELECT * FROM Account")
    Account getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Update
    void update(Account accountRow);

    @Query("SELECT * FROM Account WHERE key = :key")
    Account getByKey(String key);
}

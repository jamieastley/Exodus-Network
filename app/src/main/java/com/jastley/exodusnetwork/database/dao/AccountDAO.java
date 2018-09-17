package com.jastley.exodusnetwork.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.exodusnetwork.database.models.Account;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by jamie on 9/4/18.
 */

@Dao
public interface AccountDAO {

    @Query("SELECT * FROM Account")
    Maybe<List<Account>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Account> account);

    @Update
    void update(Account accountRow);

    @Query("SELECT * FROM Account WHERE `key` = :accountKey")
    Account getByKey(String accountKey);

    @Query("DELETE FROM Account")
    void deleteAccount();
}

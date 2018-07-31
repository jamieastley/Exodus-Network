package com.jastley.exodusnetwork.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jastley.exodusnetwork.database.dao.AccountDAO;
import com.jastley.exodusnetwork.database.models.Account;

/**
 * Created by jamie on 9/4/18.
 */

@Database(entities = {  Account.class }, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AccountDAO getAccountDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bungieAccount.db")
                    .build();
        }
        return INSTANCE;
    }

}

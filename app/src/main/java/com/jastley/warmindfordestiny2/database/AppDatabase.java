package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jastley.warmindfordestiny2.database.models.Account;
import com.jastley.warmindfordestiny2.database.models.DestinyInventoryItemDefinition;
import com.jastley.warmindfordestiny2.database.models.DestinyFactionDefinition;
import com.jastley.warmindfordestiny2.database.models.DestinyMilestoneDefinition;

/**
 * Created by jamie on 9/4/18.
 */

@Database(entities = {  Account.class,
                        DestinyInventoryItemDefinition.class,
                        DestinyFactionDefinition.class,
                        DestinyMilestoneDefinition.class }, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AccountDAO getAccountDAO();
    public abstract InventoryItemDAO getInventoryItemDAO();
    public abstract FactionsDAO getFactionsDAO();
    public abstract MilestoneDAO getMilestonesDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bungieAccount.db")
//                    .inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .build();
        }

        return INSTANCE;
    }
}

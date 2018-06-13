package com.jastley.warmindfordestiny2.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jastley.warmindfordestiny2.database.models.DestinyFactionDefinition;
import com.jastley.warmindfordestiny2.database.models.DestinyInventoryItemDefinition;
import com.jastley.warmindfordestiny2.database.models.DestinyMilestoneDefinition;

@Database(entities = {DestinyInventoryItemDefinition.class,
                    DestinyFactionDefinition.class,
                    DestinyMilestoneDefinition.class}, version = 1)

public abstract class AppManifestDatabase extends RoomDatabase {

    private static AppManifestDatabase INSTANCE;

    public abstract InventoryItemDAO getInventoryItemDAO();
    public abstract FactionsDAO getFactionsDAO();
    public abstract MilestoneDAO getMilestonesDAO();

    public static AppManifestDatabase getManifestDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppManifestDatabase.class, "bungieManifest.db")
                    .build();
        }

        return INSTANCE;
    }
}

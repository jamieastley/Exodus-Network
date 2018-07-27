package com.jastley.exodusnetwork.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.FactionsDAO;
import com.jastley.exodusnetwork.database.InventoryItemDAO;
import com.jastley.exodusnetwork.database.MilestoneDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppManifestDatabase mManifestDatabase;
    private AppDatabase mAppDatabase;

    public RoomModule(Application application) {
        mManifestDatabase = Room.databaseBuilder(application, AppManifestDatabase.class, "bungieManifest.db").build();
        mAppDatabase = Room.databaseBuilder(application, AppDatabase.class, "bungieAccount.db").build();
    }

    @Singleton
    @Provides
    AppManifestDatabase providesRoomDatabase() {
        return mManifestDatabase;
    }

    @Singleton
    @Provides
    MilestoneDAO providesMilestoneDao(AppManifestDatabase appManifestDatabase) {
        return mManifestDatabase.getMilestonesDAO();
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase() {
        return mAppDatabase;
    }

    @Singleton
    @Provides
    InventoryItemDAO providesInventoryItemDao(AppManifestDatabase appManifestDatabase) {
        return mManifestDatabase.getInventoryItemDAO();
    }

    @Provides
    @Singleton
    FactionsDAO providesFactionDao() {
        return mManifestDatabase.getFactionsDAO();
    }

}

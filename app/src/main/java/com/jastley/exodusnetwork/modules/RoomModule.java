package com.jastley.exodusnetwork.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.dao.FactionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MilestoneDAO;

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
    InventoryItemDefinitionDAO providesInventoryItemDao(AppManifestDatabase appManifestDatabase) {
        return mManifestDatabase.getInventoryItemDAO();
    }

    @Provides
    @Singleton
    FactionDefinitionDAO providesFactionDao() {
        return mManifestDatabase.getFactionsDAO();
    }

}

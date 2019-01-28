package com.jastley.exodusnetwork.modules;

import android.app.Application;
import androidx.room.Room;

import com.jastley.exodusnetwork.database.AppDatabase;
import com.jastley.exodusnetwork.database.AppManifestDatabase;
import com.jastley.exodusnetwork.database.dao.ChecklistDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.FactionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MilestoneDAO;
import com.jastley.exodusnetwork.database.dao.StatDefinitionDAO;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppManifestDatabase mManifestDatabase;
    private AppDatabase mAppDatabase;

    public RoomModule(Application application) {
//        mManifestDatabase = Room.databaseBuilder(application, AppManifestDatabase.class, "bungieManifest.db").build();
        mManifestDatabase = AppManifestDatabase.getManifestDatabase(application);
//        mAppDatabase = Room.databaseBuilder(application, AppDatabase.class, "bungieAccount.db").build();
        mAppDatabase = AppDatabase.getAppDatabase(application);
    }

    @Singleton
    @Provides
    AppManifestDatabase providesManifestDatabase() {
        return mManifestDatabase;
    }

    @Singleton
    @Provides
    MilestoneDAO providesMilestoneDao(AppManifestDatabase appManifestDatabase) {
        return mManifestDatabase.getMilestonesDAO();
    }

    @Singleton
    @Provides
    StatDefinitionDAO providesStatDefinitionDAO() {
        return mManifestDatabase.getStatDefinitionDAO();
    }

    @Singleton
    @Provides
    @Named("Account")
    AppDatabase providesAccountDatabase() {
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

    @Provides
    @Singleton
    ChecklistDefinitionDAO providesChecklistDao() {
        return mManifestDatabase.getChecklistDAO();
    }
}

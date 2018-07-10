package com.jastley.warmindfordestiny2.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.jastley.warmindfordestiny2.database.AppManifestDatabase;
import com.jastley.warmindfordestiny2.database.InventoryItemDAO;
import com.jastley.warmindfordestiny2.database.MilestoneDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppManifestDatabase mManifestDatabase;

    public RoomModule(Application application) {
        mManifestDatabase = Room.databaseBuilder(application, AppManifestDatabase.class, "bungieManifest.db").build();
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
    InventoryItemDAO providesInventoryItemDaio(AppManifestDatabase appManifestDatabase) {
        return mManifestDatabase.getInventoryItemDAO();
    }

//    @Provides
//    @Singleton
//    MilestoneRepository provideMilestoneRepository(MilestoneDAO milestoneDAO) {
//        return new MilestoneRepository(milestoneDAO);
//    }

}

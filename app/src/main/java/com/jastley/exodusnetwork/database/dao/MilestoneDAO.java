package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jastley.exodusnetwork.database.models.DestinyMilestoneDefinition;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface MilestoneDAO {

    @Query("SELECT * FROM DestinyMilestoneDefinition")
    Maybe<List<DestinyMilestoneDefinition>> getAllMilestones();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DestinyMilestoneDefinition destinyMilestoneDefinition);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinyMilestoneDefinition> milestones);

    @Query("SELECT * FROM DestinyMilestoneDefinition WHERE id = :itemKey")
    Maybe<DestinyMilestoneDefinition> getMilestoneByKey(String itemKey);

    @Query("SELECT * FROM DestinyMilestoneDefinition WHERE id IN (:itemKey)")
    Maybe<List<DestinyMilestoneDefinition>> getMilestoneListByKey(List<String> itemKey);

    @Query("SELECT * FROM DestinyMilestoneDefinition WHERE id IN (:itemKey)")
    LiveData<List<DestinyMilestoneDefinition>> getMilestoneLiveDataByKey(List<String> itemKey);

    @Update
    void update(DestinyMilestoneDefinition destinyMilestoneDefinition);
}

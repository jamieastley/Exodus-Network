package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jastley.exodusnetwork.database.models.DestinyMilestoneDefinition;

import java.util.List;

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
    Maybe<List<DestinyMilestoneDefinition>> getMilestoneLiveDataByKey(List<String> itemKey);

    @Update
    void update(DestinyMilestoneDefinition destinyMilestoneDefinition);
}

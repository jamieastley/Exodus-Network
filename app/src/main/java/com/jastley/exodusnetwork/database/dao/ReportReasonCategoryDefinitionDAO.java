package com.jastley.exodusnetwork.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyReportReasonCategoryDefinition;

import java.util.List;

@Dao
public interface ReportReasonCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition")
    LiveData<DestinyReportReasonCategoryDefinition> getAllReportReasonCategoryDefinitions();

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition WHERE id = :key")
    LiveData<DestinyReportReasonCategoryDefinition> getReportReasonCategoryDefinition(String key);

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition WHERE id IN (:key)")
    LiveData<List<DestinyReportReasonCategoryDefinition>> getReportReasonCategoryDefinitionList(List<String> key);

}

package com.jastley.exodusnetwork.database.dao;

import io.reactivex.Maybe;
import androidx.room.Dao;
import androidx.room.Query;

import com.jastley.exodusnetwork.database.models.DestinyReportReasonCategoryDefinition;

import java.util.List;

@Dao
public interface ReportReasonCategoryDefinitionDAO {

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition")
    Maybe<DestinyReportReasonCategoryDefinition> getAllReportReasonCategoryDefinitions();

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition WHERE id = :key")
    Maybe<DestinyReportReasonCategoryDefinition> getReportReasonCategoryDefinition(String key);

    @Query("SELECT * FROM DestinyReportReasonCategoryDefinition WHERE id IN (:key)")
    Maybe<List<DestinyReportReasonCategoryDefinition>> getReportReasonCategoryDefinitionList(List<String> key);

}

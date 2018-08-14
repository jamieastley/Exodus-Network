package com.jastley.exodusnetwork.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jastley.exodusnetwork.database.dao.ActivityDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ActivityGraphDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ActivityModeDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ActivityModifierDAO;
import com.jastley.exodusnetwork.database.dao.ActivityTypeDAO;
import com.jastley.exodusnetwork.database.dao.BondDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ChecklistDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ClassDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.DamageTypeDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.DestinationDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.EnemyRaceDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.EquipmentSlotDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.FactionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.GenderDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.HistoricalStatsDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryBucketDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.InventoryItemDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ItemCategoryDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ItemTierTypeDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.LocationDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.LoreDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MaterialRequirementSetDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MedalTierDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.MilestoneDAO;
import com.jastley.exodusnetwork.database.dao.ObjectiveDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.PlaceDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.PlugSetDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ProgressionDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ProgressionLevelRequirementDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.RaceDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.ReportReasonCategoryDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.RewardSourceDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.SackRewardItemListDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.SandboxPerkDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.SocketCategoryDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.SocketTypeDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.StatDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.StatGroupDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.TalentGridDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.UnlockDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.VendorDefinitionDAO;
import com.jastley.exodusnetwork.database.dao.VendorGroupDefinitionDAO;
import com.jastley.exodusnetwork.database.models.DestinyActivityDefinition;
import com.jastley.exodusnetwork.database.models.DestinyActivityGraphDefinition;
import com.jastley.exodusnetwork.database.models.DestinyActivityModeDefinition;
import com.jastley.exodusnetwork.database.models.DestinyActivityModifierDefinition;
import com.jastley.exodusnetwork.database.models.DestinyActivityTypeDefinition;
import com.jastley.exodusnetwork.database.models.DestinyBondDefinition;
import com.jastley.exodusnetwork.database.models.DestinyChecklistDefinition;
import com.jastley.exodusnetwork.database.models.DestinyClassDefinition;
import com.jastley.exodusnetwork.database.models.DestinyDamageTypeDefinition;
import com.jastley.exodusnetwork.database.models.DestinyDestinationDefinition;
import com.jastley.exodusnetwork.database.models.DestinyEnemyRaceDefinition;
import com.jastley.exodusnetwork.database.models.DestinyEquipmentSlotDefinition;
import com.jastley.exodusnetwork.database.models.DestinyFactionDefinition;
import com.jastley.exodusnetwork.database.models.DestinyGenderDefinition;
import com.jastley.exodusnetwork.database.models.DestinyHistoricalStatsDefinition;
import com.jastley.exodusnetwork.database.models.DestinyInventoryBucketDefinition;
import com.jastley.exodusnetwork.database.models.DestinyInventoryItemDefinition;
import com.jastley.exodusnetwork.database.models.DestinyItemCategoryDefinition;
import com.jastley.exodusnetwork.database.models.DestinyItemTierTypeDefinition;
import com.jastley.exodusnetwork.database.models.DestinyLocationDefinition;
import com.jastley.exodusnetwork.database.models.DestinyLoreDefinition;
import com.jastley.exodusnetwork.database.models.DestinyMaterialRequirementSetDefinition;
import com.jastley.exodusnetwork.database.models.DestinyMedalTierDefinition;
import com.jastley.exodusnetwork.database.models.DestinyMilestoneDefinition;
import com.jastley.exodusnetwork.database.models.DestinyObjectiveDefinition;
import com.jastley.exodusnetwork.database.models.DestinyPlaceDefinition;
import com.jastley.exodusnetwork.database.models.DestinyPlugSetDefinition;
import com.jastley.exodusnetwork.database.models.DestinyProgressionDefinition;
import com.jastley.exodusnetwork.database.models.DestinyProgressionLevelRequirementDefinition;
import com.jastley.exodusnetwork.database.models.DestinyRaceDefinition;
import com.jastley.exodusnetwork.database.models.DestinyReportReasonCategoryDefinition;
import com.jastley.exodusnetwork.database.models.DestinyRewardSourceDefinition;
import com.jastley.exodusnetwork.database.models.DestinySackRewardItemListDefinition;
import com.jastley.exodusnetwork.database.models.DestinySandboxPerkDefinition;
import com.jastley.exodusnetwork.database.models.DestinySocketCategoryDefinition;
import com.jastley.exodusnetwork.database.models.DestinySocketTypeDefinition;
import com.jastley.exodusnetwork.database.models.DestinyStatDefinition;
import com.jastley.exodusnetwork.database.models.DestinyStatGroupDefinition;
import com.jastley.exodusnetwork.database.models.DestinyTalentGridDefinition;
import com.jastley.exodusnetwork.database.models.DestinyUnlockDefinition;
import com.jastley.exodusnetwork.database.models.DestinyVendorDefinition;
import com.jastley.exodusnetwork.database.models.DestinyVendorGroupDefinition;

@Database(entities = { DestinyActivityDefinition.class,
                    DestinyActivityGraphDefinition.class,
                    DestinyActivityModeDefinition.class,
                    DestinyActivityModifierDefinition.class,
                    DestinyActivityTypeDefinition.class,
                    DestinyBondDefinition.class,
                    DestinyChecklistDefinition.class,
                    DestinyClassDefinition.class,
                    DestinyDamageTypeDefinition.class,
                    DestinyDestinationDefinition.class,
                    DestinyEnemyRaceDefinition.class,
                    DestinyEquipmentSlotDefinition.class,
                    DestinyFactionDefinition.class,
                    DestinyGenderDefinition.class,
                    DestinyHistoricalStatsDefinition.class,
                    DestinyInventoryBucketDefinition.class,
                    DestinyInventoryItemDefinition.class,
                    DestinyItemCategoryDefinition.class,
                    DestinyItemTierTypeDefinition.class,
                    DestinyLocationDefinition.class,
                    DestinyLoreDefinition.class,
                    DestinyMaterialRequirementSetDefinition.class,
                    DestinyMedalTierDefinition.class,
                    DestinyMilestoneDefinition.class,
                    DestinyObjectiveDefinition.class,
                    DestinyPlaceDefinition.class,
                    DestinyPlugSetDefinition.class,
                    DestinyProgressionDefinition.class,
                    DestinyProgressionLevelRequirementDefinition.class,
                    DestinyRaceDefinition.class,
                    DestinyReportReasonCategoryDefinition.class,
                    DestinyRewardSourceDefinition.class,
                    DestinySackRewardItemListDefinition.class,
                    DestinySandboxPerkDefinition.class,
                    DestinySocketCategoryDefinition.class,
                    DestinySocketTypeDefinition.class,
                    DestinyStatDefinition.class,
                    DestinyStatGroupDefinition.class,
                    DestinyTalentGridDefinition.class,
                    DestinyUnlockDefinition.class,
                    DestinyVendorDefinition.class,
                    DestinyVendorGroupDefinition.class}, version = 1)

public abstract class AppManifestDatabase extends RoomDatabase {

    private static AppManifestDatabase INSTANCE;

    //initialise all manifest tables for future feature implementations

    public abstract ActivityDefinitionDAO getActivityDefinitionDAO();
    public abstract ActivityGraphDefinitionDAO getActivityGraphDAO();
    public abstract ActivityModeDefinitionDAO getActivityModeDefinitionDAO();
    public abstract ActivityModifierDAO getActivityModifierDAO();
    public abstract ActivityTypeDAO getActivityTypeDAO();
    public abstract BondDefinitionDAO getBondDefinitionDAO();
    public abstract ChecklistDefinitionDAO getChecklistDAO();
    public abstract ClassDefinitionDAO getClassDefinitionDAO();
    public abstract DamageTypeDefinitionDAO getDamageTypeDAO();
    public abstract DestinationDefinitionDAO getDestinationDAO();
    public abstract EnemyRaceDefinitionDAO getEnemyRaceDAO();
    public abstract EquipmentSlotDefinitionDAO getEquipmentSlotDAO();
    public abstract FactionDefinitionDAO getFactionsDAO();
    public abstract GenderDefinitionDAO getGenderDAO();
    public abstract HistoricalStatsDefinitionDAO getHistoricalStatsDAO();
    public abstract InventoryBucketDefinitionDAO getInventoryBucketDAO();
    public abstract InventoryItemDefinitionDAO getInventoryItemDAO();
    public abstract ItemCategoryDefinitionDAO getItemCategoryDAO();
    public abstract ItemTierTypeDefinitionDAO getItemTierTypeDAO();
    public abstract LocationDefinitionDAO getLocationDAO();
    public abstract LoreDefinitionDAO getLoreDAO();
    public abstract MaterialRequirementSetDefinitionDAO getMaterialRequirementSetDAO();
    public abstract MedalTierDefinitionDAO getMedalTierDAO();
    public abstract MilestoneDAO getMilestonesDAO();
    public abstract ObjectiveDefinitionDAO getObjectiveDAO();
    public abstract PlaceDefinitionDAO getPlaceDAO();
    public abstract PlugSetDefinitionDAO getPlugSetDAO();
    public abstract ProgressionDefinitionDAO getProgressionDAO();
    public abstract ProgressionLevelRequirementDefinitionDAO getProgressionLevelRequirementDAO();
    public abstract RaceDefinitionDAO getRaceDAO();
    public abstract ReportReasonCategoryDefinitionDAO getReportReasonDAO();
    public abstract RewardSourceDefinitionDAO getRewardSourceDAO();
    public abstract SackRewardItemListDefinitionDAO getSackRewardItemListDAO();
    public abstract SandboxPerkDefinitionDAO getSandboxPerkDAO();
    public abstract SocketCategoryDefinitionDAO getSocketCategoryDAO();
    public abstract SocketTypeDefinitionDAO getSocketTypeDAO();
    public abstract StatDefinitionDAO getStatDefinitionDAO();
    public abstract StatGroupDefinitionDAO getStatGroupDAO();
    public abstract TalentGridDefinitionDAO getTalentGridDAO();
    public abstract UnlockDefinitionDAO getUnlockDefinitionDAO();
    public abstract VendorDefinitionDAO getVendorDefinitionDAO();
    public abstract VendorGroupDefinitionDAO getVendorGroupDAO();


    public static AppManifestDatabase getManifestDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppManifestDatabase.class, "bungieManifest.db")
//                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}

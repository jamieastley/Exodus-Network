package com.jastley.exodusnetwork.database.jsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class InventoryItemJsonData {

    private ItemData itemData;
    private String message;
    private Throwable throwable;
    private List<InventoryItemJsonData> itemDataList;

    public InventoryItemJsonData() {
    }

    public InventoryItemJsonData(String message) {
        this.message = message;
    }

    public InventoryItemJsonData(Throwable throwable) {
        this.throwable = throwable;
    }

    public InventoryItemJsonData(List<InventoryItemJsonData> itemDataList) {
        this.itemDataList = itemDataList;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public List<InventoryItemJsonData> getItemDataList() {
        return itemDataList;
    }

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("screenshot")
    private String screenshot;
    @Expose
    @SerializedName("itemTypeDisplayName")
    private String itemTypeDisplayName;
    @Expose
    @SerializedName("uiItemDisplayStyle")
    private String uiItemDisplayStyle;
    @Expose
    @SerializedName("itemTypeAndTierDisplayName")
    private String itemTypeAndTierDisplayName;
    @Expose
    @SerializedName("action")
    private Action action;
    @Expose
    @SerializedName("inventory")
    private Inventory inventory;
    @Expose
    @SerializedName("stats")
    private Stats stats;
    @Expose
    @SerializedName("equippingBLock")
    private EquippingBlock equippingBlock;
    @Expose
    @SerializedName("quality")
    private Quality quality;
    @Expose
    @SerializedName("sourceData")
    private SourceData sourceData;
    @Expose
    @SerializedName("objectives")
    private Objectives objectives;
    @Expose
    @SerializedName("acquireUnlockHash")
    private String acquireUnlockHash;
    @Expose
    @SerializedName("sockets")
    private Sockets sockets;
    @Expose
    @SerializedName("talentGrid")
    private TalentGrid talentGrid;
    @Expose
    @SerializedName("investmentStats")
    private List<InvestmentStats> investmentStatsList;
    @Expose
    @SerializedName("perks")
    private List<Perks> perksList;
    @Expose
    @SerializedName("loreHash")
    private String loreHash;
    @Expose
    @SerializedName("summaryItemHash")
    private String summaryItemHash;
    @Expose
    @SerializedName("allowActions")
    private boolean allowActions;
    @Expose
    @SerializedName("doesPostmasterPullHaveSideEffects")
    private boolean doesPostmasterPullHaveSideEffects;
    @Expose
    @SerializedName("nonTransferrable")
    private boolean nonTransferrable;
    @Expose
    @SerializedName("itemCategoryHashes")
    private List<String> itemCategoryHashes;
    @Expose
    @SerializedName("specialItemType")
    private String specialItemType;
    @Expose
    @SerializedName("itemType")
    private int itemType;
    @Expose
    @SerializedName("itemSubType")
    private int itemSubType;
    @Expose
    @SerializedName("classType")
    private int classType;
    @Expose
    @SerializedName("equippable")
    private boolean isEquippable;
    @Expose
    @SerializedName("damageTypeHashes")
    private List<String> damageTypeHashesList;
    @Expose
    @SerializedName("damageTypes")
    private List<String> damageTypesList;
    @Expose
    @SerializedName("defaultDamageType")
    private int defaultDamageType;
    @Expose
    @SerializedName("defaultDamageTypeHash")
    private String defaultDamageTypeHash;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("index")
    private String index;
    @Expose
    @SerializedName("redacted")
    private boolean isRedacted;
    @Expose
    @SerializedName("blacklisted")
    private boolean isBlacklisted;

    //Getters
    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public String getItemTypeDisplayName() {
        return itemTypeDisplayName;
    }

    public String getUiItemDisplayStyle() {
        return uiItemDisplayStyle;
    }

    public String getItemTypeAndTierDisplayName() {
        return itemTypeAndTierDisplayName;
    }

    public Action getAction() {
        return action;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Stats getStats() {
        return stats;
    }

    public EquippingBlock getEquippingBlock() {
        return equippingBlock;
    }

    public Quality getQuality() {
        return quality;
    }

    public SourceData getSourceData() {
        return sourceData;
    }

    public Objectives getObjectives() {
        return objectives;
    }

    public String getAcquireUnlockHash() {
        return acquireUnlockHash;
    }

    public Sockets getSockets() {
        return sockets;
    }

    public TalentGrid getTalentGrid() {
        return talentGrid;
    }

    public List<InvestmentStats> getInvestmentStatsList() {
        return investmentStatsList;
    }

    public List<Perks> getPerksList() {
        return perksList;
    }

    public String getLoreHash() {
        return loreHash;
    }

    public String getSummaryItemHash() {
        return summaryItemHash;
    }

    public boolean isAllowActions() {
        return allowActions;
    }

    public boolean isDoesPostmasterPullHaveSideEffects() {
        return doesPostmasterPullHaveSideEffects;
    }

    public boolean isNonTransferrable() {
        return nonTransferrable;
    }

    public List<String> getItemCategoryHashes() {
        return itemCategoryHashes;
    }

    public String getSpecialItemType() {
        return specialItemType;
    }

    public int getItemType() {
        return itemType;
    }

    public int getItemSubType() {
        return itemSubType;
    }

    public int getClassType() {
        return classType;
    }

    public boolean isEquippable() {
        return isEquippable;
    }

    public List<String> getDamageTypeHashesList() {
        return damageTypeHashesList;
    }

    public List<String> getDamageTypesList() {
        return damageTypesList;
    }

    public int getDefaultDamageType() {
        return defaultDamageType;
    }

    public String getDefaultDamageTypeHash() {
        return defaultDamageTypeHash;
    }

    public String getHash() {
        return hash;
    }

    public String getIndex() {
        return index;
    }

    public boolean isRedacted() {
        return isRedacted;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setDisplayProperties(DisplayProperties displayProperties) {
        this.displayProperties = displayProperties;
    }

    public static class DisplayProperties {
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("icon")
        private String icon;
        @Expose
        @SerializedName("hasIcon")
        private boolean hasIcon;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }
    }

    public static class Action {
        @Expose
        @SerializedName("verbName")
        private String verbName;
        @Expose
        @SerializedName("verbDescription")
        private String verbDescription;
        @Expose
        @SerializedName("isPositive")
        private boolean isPosititve;
        @Expose
        @SerializedName("requiredCooldownSeconds")
        private String requiredCooldownSeconds;
//        @Expose
//        @SerializedName("requiredItems")
//        private String[] requiredItems;
        @Expose
        @SerializedName("progressionRewards")
        private String[] progressionRewards;
        @Expose
        @SerializedName("actionTypeLabel")
        private String actionTypeLabel;
        @Expose
        @SerializedName("rewardSheetHash")
        private String rewardSheetHash;
        @Expose
        @SerializedName("rewardItemHash")
        private String rewardItemHash;
        @Expose
        @SerializedName("rewardSiteHash")
        private String rewardSiteHash;
        @Expose
        @SerializedName("requiredCooldownHash")
        private String requiredCooldownHash;
        @Expose
        @SerializedName("deleteOnAction")
        private boolean willDeleteOnAction;
        @Expose
        @SerializedName("consumeEntireStack")
        private boolean willConsumeEntireStack;
        @Expose
        @SerializedName("useOnAcquire")
        private boolean willUseOnAcquire;

        public String getVerbName() {
            return verbName;
        }

        public String getVerbDescription() {
            return verbDescription;
        }

        public boolean isPosititve() {
            return isPosititve;
        }

        public String getRequiredCooldownSeconds() {
            return requiredCooldownSeconds;
        }

//        public String[] getRequiredItems() {
//            return requiredItems;
//        }

        public String[] getProgressionRewards() {
            return progressionRewards;
        }

        public String getActionTypeLabel() {
            return actionTypeLabel;
        }

        public String getRewardSheetHash() {
            return rewardSheetHash;
        }

        public String getRewardItemHash() {
            return rewardItemHash;
        }

        public String getRewardSiteHash() {
            return rewardSiteHash;
        }

        public String getRequiredCooldownHash() {
            return requiredCooldownHash;
        }

        public boolean willDeleteOnAction() {
            return willDeleteOnAction;
        }

        public boolean willConsumeEntireStack() {
            return willConsumeEntireStack;
        }

        public boolean willUseOnAcquire() {
            return willUseOnAcquire;
        }
    }

    public static class Inventory {
        @Expose
        @SerializedName("maxStackSize")
        private int maxStackSize;
        @Expose
        @SerializedName("bucketTypeHash")
        private String bucketTypeHash;
        @Expose
        @SerializedName("recoveryBucketTypeHash")
        private String recoveryBucketTypeHash;
        @Expose
        @SerializedName("tierTypeHash")
        private String tierTypeHash;
        @Expose
        @SerializedName("isInstanceItem")
        private boolean isInstanceItem;
        @Expose
        @SerializedName("nonTransferrableOriginal")
        private boolean isNonTransferrableOriginal;
        @Expose
        @SerializedName("tierTypeName")
        private String tierTypeName;
        @Expose
        @SerializedName("tierType")
        private int tierType;

        public int getMaxStackSize() {
            return maxStackSize;
        }

        public String getBucketTypeHash() {
            return bucketTypeHash;
        }

        public String getRecoveryBucketTypeHash() {
            return recoveryBucketTypeHash;
        }

        public String getTierTypeHash() {
            return tierTypeHash;
        }

        public boolean isInstanceItem() {
            return isInstanceItem;
        }

        public boolean isNonTransferrableOriginal() {
            return isNonTransferrableOriginal;
        }

        public String getTierTypeName() {
            return tierTypeName;
        }

        public int getTierType() {
            return tierType;
        }
    }

    public static class Stats {
        @Expose
        @SerializedName("disablePrimaryStatDisplay")
        private boolean willDisablePrimaryStatDisplay;
        @Expose
        @SerializedName("statGroupHash")
        private String statGroupHash;
        @Expose
        @SerializedName("stats")
        private Map<String, StatValues> statValues;
        @Expose
        @SerializedName("hasDisplayableStats")
        private boolean hasDisplayableStats;
        @Expose
        @SerializedName("primaryBaseStatHash")
        private String primaryBaseStatHash;

        public boolean isWillDisablePrimaryStatDisplay() {
            return willDisablePrimaryStatDisplay;
        }

        public String getStatGroupHash() {
            return statGroupHash;
        }

        public Map<String, StatValues> getStatValues() {
            return statValues;
        }

        public boolean isHasDisplayableStats() {
            return hasDisplayableStats;
        }

        public String getPrimaryBaseStatHash() {
            return primaryBaseStatHash;
        }

        public static class StatValues {
            @Expose
            @SerializedName("statHash")
            private String statHash;
            @Expose
            @SerializedName("value")
            private String value;
            @Expose
            @SerializedName("minimum")
            private String minimum;
            @Expose
            @SerializedName("maximum")
            private String maximum;

            public String getStatHash() {
                return statHash;
            }

            public String getValue() {
                return value;
            }

            public String getMinimum() {
                return minimum;
            }

            public String getMaximum() {
                return maximum;
            }
        }
    }

    public static class EquippingBlock {
        @Expose
        @SerializedName("uniqueLabel")
        private String uniqueLabel;
        @Expose
        @SerializedName("uniqueLabelHash")
        private String uniqueLabelHash;
        @Expose
        @SerializedName("equipmentSlotTypeHash")
        private String equipmentSlotTypeHash;
        @Expose
        @SerializedName("attributes")
        private String attributes;
        @Expose
        @SerializedName("equippingSoundHash")
        private String equippingSoundHash;
        @Expose
        @SerializedName("hornSoundHash")
        private String hornSoundHash;
        @Expose
        @SerializedName("displayStrings")
        private List<String> displayStringsList;

        public String getUniqueLabel() {
            return uniqueLabel;
        }

        public String getUniqueLabelHash() {
            return uniqueLabelHash;
        }

        public String getEquipmentSlotTypeHash() {
            return equipmentSlotTypeHash;
        }

        public String getAttributes() {
            return attributes;
        }

        public String getEquippingSoundHash() {
            return equippingSoundHash;
        }

        public String getHornSoundHash() {
            return hornSoundHash;
        }

        public List<String> getDisplayStringsList() {
            return displayStringsList;
        }
    }

    public static class Quality {
        @Expose
        @SerializedName("itemLevels")
        private List<String> itemLevelsList;
        @Expose
        @SerializedName("qualityLevel")
        private String qualityLevel;
        @Expose
        @SerializedName("infusionCategoryName")
        private String infusionCategoryName;
        @Expose
        @SerializedName("infusionCategoryHash")
        private String infusionCategoryHash;
        @Expose
        @SerializedName("infusionCategoryHashes")
        private List<String> infusionCategoryHashesList;
        @Expose
        @SerializedName("progressionLevelRequirementHash")
        private String progressionLevelRequirementHash;

        public List<String> getItemLevelsList() {
            return itemLevelsList;
        }

        public String getQualityLevel() {
            return qualityLevel;
        }

        public String getInfusionCategoryName() {
            return infusionCategoryName;
        }

        public String getInfusionCategoryHash() {
            return infusionCategoryHash;
        }

        public List<String> getInfusionCategoryHashesList() {
            return infusionCategoryHashesList;
        }

        public String getProgressionLevelRequirementHash() {
            return progressionLevelRequirementHash;
        }
    }

    public static class SourceData {
        @Expose
        @SerializedName("sourceHashes")
        private List<String> sourceHashesList;
        @Expose
        @SerializedName("sources")
        private List<Sources> sourcesList;
        @Expose
        @SerializedName("exclusive")
        private String exclusive;

        public List<String> getSourceHashesList() {
            return sourceHashesList;
        }

        public List<Sources> getSourcesList() {
            return sourcesList;
        }

        public String getExclusive() {
            return exclusive;
        }

        public static class Sources {
            @Expose
            @SerializedName("level")
            private String level;
            @Expose
            @SerializedName("minQuality")
            private String minQuality;
            @Expose
            @SerializedName("maxQuality")
            private String maxQuality;
            @Expose
            @SerializedName("minLevelRequired")
            private String minLevelRequired;
            @Expose
            @SerializedName("maxLevelRequired")
            private String maxLevelRequired;
            @Expose
            @SerializedName("exclusivity")
            private String exclusivity;
            @Expose
            @SerializedName("computedStats")
            private Map<String, ComputedStats> computedStats;
            @Expose
            @SerializedName("sourceHashes")
            private List<String> sourceHashes;

            //Getters
            public String getLevel() {
                return level;
            }

            public String getMinQuality() {
                return minQuality;
            }

            public String getMaxQuality() {
                return maxQuality;
            }

            public String getMinLevelRequired() {
                return minLevelRequired;
            }

            public String getMaxLevelRequired() {
                return maxLevelRequired;
            }

            public String getExclusivity() {
                return exclusivity;
            }

            public Map<String, ComputedStats> getComputedStats() {
                return computedStats;
            }

            public List<String> getSourceHashes() {
                return sourceHashes;
            }

            public static class ComputedStats {
                @Expose
                @SerializedName("statHash")
                private String statHash;
                @Expose
                @SerializedName("value")
                private String value;
                @Expose
                @SerializedName("minimum")
                private String minimum;
                @Expose
                @SerializedName("maximum")
                private String maximum;

                public String getStatHash() {
                    return statHash;
                }

                public String getValue() {
                    return value;
                }

                public String getMinimum() {
                    return minimum;
                }

                public String getMaximum() {
                    return maximum;
                }
            }
        }


    }

    public static class Objectives {
        @Expose
        @SerializedName("objectiveHashes")
        private List<String> objectiveHashesList;
        @Expose
        @SerializedName("displayActivityHashes")
        private List<String> displayActivityHashes;
        @Expose
        @SerializedName("requireFullObjectiveCompletion")
        private boolean doesRequireFullObjectiveCompletion;
        @Expose
        @SerializedName("questlineItemHash")
        private String questlineItemHash;
        @Expose
        @SerializedName("narrative")
        private String narrative;
        @Expose
        @SerializedName("objectiveVerbName")
        private String objectiveVerbName;
        @Expose
        @SerializedName("questTypeIdentifier")
        private String questTypeIdentifier;
        @Expose
        @SerializedName("questTypeHash")
        private String questTypeHash;
        @Expose
        @SerializedName("completionRewardSiteHash")
        private String completionRewardSiteHash;
        @Expose
        @SerializedName("nextQuestStepRewardSiteHash")
        private String nextQuestStepRewardSiteHash;
        @Expose
        @SerializedName("timestampUnlockValueHash")
        private String timestampUnlockValueHash;
        @Expose
        @SerializedName("isGlobalObjectiveItem")
        private boolean isGlobalObjectiveItem;
        @Expose
        @SerializedName("useOnObjectiveCompletion")
        private boolean useOnObjectiveCompletion;
        @Expose
        @SerializedName("inhibitCompletionUnlockValueHash")
        private String inhibitCompletionUnlockValueHash;

        public List<String> getObjectiveHashesList() {
            return objectiveHashesList;
        }

        public List<String> getDisplayActivityHashes() {
            return displayActivityHashes;
        }

        public boolean isDoesRequireFullObjectiveCompletion() {
            return doesRequireFullObjectiveCompletion;
        }

        public String getQuestlineItemHash() {
            return questlineItemHash;
        }

        public String getNarrative() {
            return narrative;
        }

        public String getObjectiveVerbName() {
            return objectiveVerbName;
        }

        public String getQuestTypeIdentifier() {
            return questTypeIdentifier;
        }

        public String getQuestTypeHash() {
            return questTypeHash;
        }

        public String getCompletionRewardSiteHash() {
            return completionRewardSiteHash;
        }

        public String getNextQuestStepRewardSiteHash() {
            return nextQuestStepRewardSiteHash;
        }

        public String getTimestampUnlockValueHash() {
            return timestampUnlockValueHash;
        }

        public boolean isGlobalObjectiveItem() {
            return isGlobalObjectiveItem;
        }

        public boolean isUseOnObjectiveCompletion() {
            return useOnObjectiveCompletion;
        }

        public String getInhibitCompletionUnlockValueHash() {
            return inhibitCompletionUnlockValueHash;
        }
    }

    public static class Sockets {
        @Expose
        @SerializedName("detail")
        private String detail;
        @Expose
        @SerializedName("socketEntries")
        private List<SocketEntries> socketEntriesList;
        @Expose
        @SerializedName("intrinsicSockets")
        private List<IntrinsicSockets> intrinsicSocketsList;
        @Expose
        @SerializedName("socketCategories")
        private List<SocketCategories> socketCategoriesList;

        //Getters
        public String getDetail() {
            return detail;
        }

        public List<SocketEntries> getSocketEntriesList() {
            return socketEntriesList;
        }

        public List<IntrinsicSockets> getIntrinsicSocketsList() {
            return intrinsicSocketsList;
        }

        public List<SocketCategories> getSocketCategoriesList() {
            return socketCategoriesList;
        }

        public static class SocketEntries {
            @Expose
            @SerializedName("socketTypeHash")
            private String socketTypeHash;
            @Expose
            @SerializedName("singleInitialItemHash")
            private String singleInitialItemHash;
            @Expose
            @SerializedName("reusablePlugItems")
            private List<PlugItemHash> plugItemHashList;
            @Expose
            @SerializedName("preventInitializationOnVendorPurchase")
            private boolean doesPreventInitializationOnVendorPurchase;
            @Expose
            @SerializedName("preventInitializationWhenVersioning")
            private boolean doesPreventInitializationWhenVersioning;
            @Expose
            @SerializedName("hidePerksInItemTooltip")
            private boolean doesHidePerksInItemTooltip;
            @Expose
            @SerializedName("plugSources")
            private String plugSources;

            //Getters

            public String getSocketTypeHash() {
                return socketTypeHash;
            }

            public String getSingleInitialItemHash() {
                return singleInitialItemHash;
            }

            public List<PlugItemHash> getPlugItemHashList() {
                return plugItemHashList;
            }

            public boolean isDoesPreventInitializationOnVendorPurchase() {
                return doesPreventInitializationOnVendorPurchase;
            }

            public boolean isDoesPreventInitializationWhenVersioning() {
                return doesPreventInitializationWhenVersioning;
            }

            public boolean isDoesHidePerksInItemTooltip() {
                return doesHidePerksInItemTooltip;
            }

            public String getPlugSources() {
                return plugSources;
            }

            public static class PlugItemHash {
                @Expose
                @SerializedName("plugItemHash")
                private String plugItemHash;

                public String getPlugItemHash() {
                    return plugItemHash;
                }
            }
        }

        public static class IntrinsicSockets {
            @Expose
            @SerializedName("plugItemHash")
            private String plugItemHash;
            @Expose
            @SerializedName("socketTypeHash")
            private String socketTypeHash;

            public String getPlugItemHash() {
                return plugItemHash;
            }

            public String getSocketTypeHash() {
                return socketTypeHash;
            }
        }

        public static class SocketCategories {
            @Expose
            @SerializedName("socketCategoryHash")
            private String socketCategoryHash;
            @Expose
            @SerializedName("socketIndexes")
            private List<Integer> socketIndexes;

            public String getSocketCategoryHash() {
                return socketCategoryHash;
            }

            public List<Integer> getSocketIndexes() {
                return socketIndexes;
            }
        }
    }

    public static class TalentGrid {
        @Expose
        @SerializedName("talentGridHash")
        private String talentGridHash;
        @Expose
        @SerializedName("itemDetailString")
        private String itemDetailString;
        @Expose
        @SerializedName("hudDamageType")
        private int hudDamageType;

        public String getTalentGridHash() {
            return talentGridHash;
        }

        public String getItemDetailString() {
            return itemDetailString;
        }

        public int getHudDamageType() {
            return hudDamageType;
        }
    }

    public static class InvestmentStats {
        @Expose
        @SerializedName("statTypeHash")
        private String statTypeHash;
        @Expose
        @SerializedName("value")
        private int value;
        @Expose
        @SerializedName("isConditionallyActive")
        private boolean isConditionallyActive;

        public String getStatTypeHash() {
            return statTypeHash;
        }

        public int getValue() {
            return value;
        }

        public boolean isConditionallyActive() {
            return isConditionallyActive;
        }
    }

    public static class Perks {
        //TODO when I found out the values
    }

    //Instance data for inventory
    public class ItemData {
        @Expose
        @SerializedName("dismantlePermission")
        private int dismantlePermission;
        @Expose
        @SerializedName("state")
        private int state;
        @Expose
        @SerializedName("lockable")
        private boolean lockable;
        @Expose
        @SerializedName("transferStatus")
        private int transferStatus;
        @Expose
        @SerializedName("bucketHash")
        private String bucketHash;
        @Expose
        @SerializedName("location")
        private int location;
        @Expose
        @SerializedName("bindStatus")
        private int bindStatus;
        @Expose
        @SerializedName("quantity")
        private int quantity;
        @Expose
        @SerializedName("itemInstanceId")
        private String itemInstanceId;
        @Expose
        @SerializedName("itemHash")
        private String itemHash;

        public int getDismantlePermission() {
            return dismantlePermission;
        }

        public int getState() {
            return state;
        }

        public boolean getLockable() {
            return lockable;
        }

        public int getTransferStatus() {
            return transferStatus;
        }

        public String getBucketHash() {
            return bucketHash;
        }

        public int getLocation() {
            return location;
        }

        public int getBindStatus() {
            return bindStatus;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getItemInstanceId() {
            return itemInstanceId;
        }

        public String getItemHash() {
            return itemHash;
        }
    }

    public ItemData getItemData() {
        return itemData;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    public static class InstanceData {
        @Expose
        @SerializedName("damageType")
        private String damageType;
        @Expose
        @SerializedName("damageTypeHash")
        private Double damageTypeHash;
        @Expose
        @SerializedName("itemLevel")
        private int itemLevel;
        @Expose
        @SerializedName("quality")
        private int quality;
        @Expose
        @SerializedName("isEquipped")
        private boolean isEquipped;
        @Expose
        @SerializedName("canEquip")
        private boolean canEquip;
        @Expose
        @SerializedName("equipRequiredLevel")
        private int equipRequiredLevel;
        @Expose
        @SerializedName("primaryStat")
        private PrimaryStat primaryStat;

        public PrimaryStat getPrimaryStat() {
            return primaryStat;
        }

        public String getDamageType() {
            return damageType;
        }

        public Double getDamageTypeHash() {
            return damageTypeHash;
        }

        public int getItemLevel() {
            return itemLevel;
        }

        public int getQuality() {
            return quality;
        }

        public boolean getIsEquipped() {
            return isEquipped;
        }

        public boolean getCanEquip() {
            return canEquip;
        }

        public int getEquipRequiredLevel() {
            return equipRequiredLevel;
        }

        public static class PrimaryStat {
            @Expose
            @SerializedName("statHash")
            private Double statHash;
            @Expose
            @SerializedName("value")
            private String value;
            @Expose
            @SerializedName("maximumValue")
            private int maximumValue;

            public Double getStatHash() {
                return statHash;
            }

            public String getValue() {
                return value;
            }

            public int getMaximumValue() {
                return maximumValue;
            }
        }
    }

}

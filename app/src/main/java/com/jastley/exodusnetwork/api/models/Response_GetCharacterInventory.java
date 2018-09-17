package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by jamie on 21/3/18.
 */

public class Response_GetCharacterInventory {


    @Expose
    @SerializedName("MessageData")
    private MessageData MessageData;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("ErrorStatus")
    private String ErrorStatus;
    @Expose
    @SerializedName("ThrottleSeconds")
    private int ThrottleSeconds;
    @Expose
    @SerializedName("ErrorCode")
    private String ErrorCode;
    @Expose
    @SerializedName("Response")
    private Response Response;

    public MessageData getMessageData() {
        return MessageData;
    }

    public String getMessage() {
        return Message;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public int getThrottleSeconds() {
        return ThrottleSeconds;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public Response getResponse() {
        return Response;
    }

    public static class MessageData {
    }

    public static class Response {
        @Expose
        @SerializedName("itemComponents")
        private ItemComponents itemComponents;
        @Expose
        @SerializedName("inventory")
        private Inventory inventory;
        @Expose
        @SerializedName("equipment")
        private Equipment equipment;

        @Expose
        @SerializedName("profileInventory")
        private ProfileInventory profileInventory;

        public ItemComponents getItemComponents() {
            return itemComponents;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public ProfileInventory getProfileInventory() {
            return profileInventory;
        }

        public Equipment getEquipment() {
            return equipment;
        }


    }
    public static class Equipment {
        @Expose
        @SerializedName("privacy")
        private int privacy;
        @Expose
        @SerializedName("data")
        private EquipmentData equipmentData;

        public int getPrivacy() {
            return privacy;
        }

        public EquipmentData getEquipmentData() {
            return equipmentData;
        }
    }

    public static class EquipmentData {
        @Expose
        @SerializedName("items")
        private List<Items> equipmentItems;
//        private List<EquipmentItems> items;
//
//        public List<EquipmentItems> getEquipmentItems() {
//            return items;
//        }


        public List<Items> getEquipmentItems() {
            return equipmentItems;
        }

    }

    public static class EquipmentItems {
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

    public static class ItemComponents {
        @Expose
        @SerializedName("instances")
        private Instances instances;
        @Expose
        @SerializedName("objectives")
        private Objectives objectives;

        public Instances getInstances() {
            return instances;
        }

        public Objectives getObjectives() {
            return objectives;
        }
    }

    public static class Instances {
        @Expose
        @SerializedName("data")
        private Map<String, InstanceData>  data;

        public Map<String, InstanceData> getInstanceData() {
            return data;
        }
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
        @Expose
        @SerializedName("cannotEquipReason")
        private int cannotEquipReason;

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

        public int getCannotEquipReason() {
            return cannotEquipReason;
        }
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

    public static class ProfileInventory {
        @Expose
        @SerializedName("privacy")
        private int privacy;
        @Expose
        @SerializedName("data")
        private Data data;

        public int getPrivacy() {
            return privacy;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Inventory {
        @Expose
        @SerializedName("privacy")
        private int privacy;
        @Expose
        @SerializedName("data")
        private Data data;

        public int getPrivacy() {
            return privacy;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        @Expose
        @SerializedName("items")
        private List<Items> items;

        public List<Items> getItems() {
            return items;
        }
    }

    public static class Items {
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

    public static class Objectives {
        @Expose
        @SerializedName("data")
        private Map<String, ObjectiveData> getData;

        public Map<String, ObjectiveData> getData() {
            return getData;
        }

        public static class ObjectiveData {
            @Expose
            @SerializedName("objectives")
            private List<ItemObjectives> itemObjectives;
            @Expose
            @SerializedName("flavorObjective")
            private ItemObjectives flavorObjective;

            public List<ItemObjectives> getItemObjectives() {
                return itemObjectives;
            }

            public ItemObjectives getFlavorObjective() {
                return flavorObjective;
            }

            public static class ItemObjectives {
                @Expose
                @SerializedName("objectiveHash")
                private String objectiveHash;
                @Expose
                @SerializedName("progress")
                private int progress;
                @Expose
                @SerializedName("completionValue")
                private int completionValue;
                @Expose
                @SerializedName("complete")
                private boolean isComplete;
                @Expose
                @SerializedName("visible")
                private boolean isVisible;

                private String progressDescription;

                private String objectiveDataName;

                public String getObjectiveHash() {
                    return objectiveHash;
                }

                public int getProgress() {
                    return progress;
                }

                public int getCompletionValue() {
                    return completionValue;
                }

                public boolean isComplete() {
                    return isComplete;
                }

                public boolean isVisible() {
                    return isVisible;
                }

                public String getObjectiveDataName() {
                    return objectiveDataName;
                }

                public void setObjectiveDataName(String objectiveDataName) {
                    this.objectiveDataName = objectiveDataName;
                }

                public String getProgressDescription() {
                    return progressDescription;
                }

                public void setProgressDescription(String progressDescription) {
                    this.progressDescription = progressDescription;
                }
            }


        }
    }
}

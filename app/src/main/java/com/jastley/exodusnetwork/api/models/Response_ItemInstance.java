package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Response_ItemInstance {

    @Expose
    @SerializedName("Response")
    private Response response;

    @Expose
    @SerializedName("ErrorCode")
    private String errorCode;

    @Expose
    @SerializedName("Message")
    private String message;

    public Response getResponse() {
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static class Response {

        @Expose
        @SerializedName("characterId")
        private String characterId;

        @Expose
        @SerializedName("item")
        private Item item;

        @Expose
        @SerializedName("perks")
        private Perks perks;

        @Expose
        @SerializedName("stats")
        private Stats stats;

        @Expose
        @SerializedName("sockets")
        private Sockets sockets;

        public String getCharacterId() {
            return characterId;
        }

        public Item getItem() {
            return item;
        }

        public Perks getPerks() {
            return perks;
        }

        public Stats getStats() {
            return stats;
        }

        public Sockets getSockets() {
            return sockets;
        }
    }

    public static class Item {

        @Expose
        @SerializedName("data")
        private ItemData data;

        public ItemData getData() {
            return data;
        }
    }

    //component = 307
    public static class ItemData {
        @Expose
        @SerializedName("itemHash")
        private String itemHash;
        @Expose
        @SerializedName("itemInstanceId")
        private String itemInstanceId;
        @Expose
        @SerializedName("quantity")
        private String quantity;
        @Expose
        @SerializedName("bindStatus")
        private String bindStatus;
        @Expose
        @SerializedName("location")
        private String location;
        @Expose
        @SerializedName("bucketHash")
        private String bucketHash;
        @Expose
        @SerializedName("transferStatus")
        private String transferStatus;
        @Expose
        @SerializedName("lockable")
        private boolean lockable;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("dismantlePermission")
        private String dismantlePermission;

        public String getItemHash() {
            return itemHash;
        }

        public String getItemInstanceId() {
            return itemInstanceId;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public String getLocation() {
            return location;
        }

        public String getBucketHash() {
            return bucketHash;
        }

        public String getTransferStatus() {
            return transferStatus;
        }

        public boolean isLockable() {
            return lockable;
        }

        public String getState() {
            return state;
        }

        public String getDismantlePermission() {
            return dismantlePermission;
        }
    }

    //component = 302
    public static class Perks {
        @Expose
        @SerializedName("data")
        private PerksData perksData;
        @Expose
        @SerializedName("privacy")
        private int privacy;

        public PerksData getPerksData() {
            return perksData;
        }

        public int getPrivacy() {
            return privacy;
        }
    }

    public static class PerksData {
        @Expose
        @SerializedName("perks")
        private List<PerkDetails> perkDetailsList;

        public List<PerkDetails> getPerkDetailsList() {
            return perkDetailsList;
        }
    }

    public static class PerkDetails {
        @Expose
        @SerializedName("perkHash")
        private String perkHash;
        @Expose
        @SerializedName("iconPath")
        private String iconPath;
        @Expose
        @SerializedName("isActive")
        private boolean isActive;
        @Expose
        @SerializedName("visible")
        private boolean isVisible;

        public String getPerkHash() {
            return perkHash;
        }

        public String getIconPath() {
            return iconPath;
        }

        public boolean getIsActive() {
            return isActive;
        }

        public boolean getIsVisible() {
            return isVisible;
        }
    }

    public static class Stats {
        @Expose
        @SerializedName("data")
        private Map<String, StatsData> statsData;
        @Expose
        @SerializedName("privacy")
        private int privacy;

        public Map<String, StatsData> getStatsData() {
            return statsData;
        }

        public int getPrivacy() {
            return privacy;
        }
    }

    public static class StatsData {
        @Expose
        @SerializedName("statHash")
        private String statHash;
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("maximumValue")
        private String maximumValue;

        public String getStatHash() {
            return statHash;
        }

        public String getValue() {
            return value;
        }

        public String getMaximumValue() {
            return maximumValue;
        }
    }

    public static class Sockets {
        @Expose
        @SerializedName("data")
        private SocketData socketData;
        @Expose
        @SerializedName("privacy")
        private int privacy;

        public SocketData getSocketData() {
            return socketData;
        }

        public int getPrivacy() {
            return privacy;
        }
    }

    public static class SocketData {
        @Expose
        @SerializedName("sockets")
        private List<SocketDetails> socketDetailsList;

        public List<SocketDetails> getSocketDetailsList() {
            return socketDetailsList;
        }
    }

    public static class SocketDetails {
        @Expose
        @SerializedName("plugHash")
        private String plugHash;
        @Expose
        @SerializedName("isEnabled")
        private boolean isEnabled;
        @Expose
        @SerializedName("reusablePlugHashes")
        private List<String> reusablePlugHashes;
        @Expose
        @SerializedName("reusablePlugs")
        private List<ReusablePlugDetails> reusablePlugDetailsList;

        public String getPlugHash() {
            return plugHash;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public List<String> getReusablePlugHashes() {
            return reusablePlugHashes;
        }

        public List<ReusablePlugDetails> getReusablePlugDetailsList() {
            return reusablePlugDetailsList;
        }


        public static class ReusablePlugDetails {
            @Expose
            @SerializedName("plugItemHash")
            private String plugItemHash;
            @Expose
            @SerializedName("canInsert")
            private boolean canInsert;
            @Expose
            @SerializedName("enabled")
            private boolean isEnabled;

            public String getPlugItemHash() {
                return plugItemHash;
            }

            public boolean isCanInsert() {
                return canInsert;
            }

            public boolean isEnabled() {
                return isEnabled;
            }
        }
    }
}

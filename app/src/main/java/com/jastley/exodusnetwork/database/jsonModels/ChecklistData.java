package com.jastley.exodusnetwork.database.jsonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChecklistData {

    @Expose
    @SerializedName("displayProperties")
    private DisplayProperties displayProperties;
    @Expose
    @SerializedName("viewActionString")
    private String viewActionString;
    @Expose
    @SerializedName("scope")
    private String scope;
    @Expose
    @SerializedName("entries")
    private List<ChecklistEntries> entriesList;
    @Expose
    @SerializedName("hash")
    private String hash;
    @Expose
    @SerializedName("index")
    private int index;
    @Expose
    @SerializedName("redacted")
    private boolean isRedacted;
    @Expose
    @SerializedName("blacklisted")
    private boolean blacklisted;

    public DisplayProperties getDisplayProperties() {
        return displayProperties;
    }

    public String getViewActionString() {
        return viewActionString;
    }

    public String getScope() {
        return scope;
    }

    public List<ChecklistEntries> getEntriesList() {
        return entriesList;
    }

    public String getHash() {
        return hash;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRedacted() {
        return isRedacted;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public static class DisplayProperties {
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("hasIcon")
        private boolean hasIcon;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }
    }

    public static class ChecklistEntries {
        @Expose
        @SerializedName("hash")
        private String hash;
        @Expose
        @SerializedName("displayProperties")
        private ChecklistDisplayProperties checklistDisplayProperties;
        @Expose
        @SerializedName("destinationHash")
        private String destinationHash;
        @Expose
        @SerializedName("bubbleHash")
        private String bubbleHash;
        @Expose
        @SerializedName("itemHash")
        private String itemHash;
        @Expose
        @SerializedName("scope")
        private String scope;

        public String getHash() {
            return hash;
        }

        public ChecklistDisplayProperties getChecklistDisplayProperties() {
            return checklistDisplayProperties;
        }

        public String getDestinationHash() {
            return destinationHash;
        }

        //for Sleeper Nodes only
        public String getItemHash() {
            return itemHash;
        }

        public String getBubbleHash() {
            return bubbleHash;
        }

        public String getScope() {
            return scope;
        }

        public static class ChecklistDisplayProperties {
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
    }
}

package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response_GetPublicFireteams {

    private List<Response.Results> fireteamsList = new ArrayList<>();
    private Throwable throwable;
    private String errorMessage;

    public Response_GetPublicFireteams(List<Response.Results> fireteamsList) {
        this.fireteamsList = fireteamsList;
        this.throwable = null;
    }

    public Response_GetPublicFireteams(Throwable throwable) {
        this.throwable = throwable;
        this.fireteamsList = null;
    }

    public Response_GetPublicFireteams(String errorMessage) {
        this.errorMessage = errorMessage;
        this.throwable = null;
        this.fireteamsList = null;
    }

    public List<Response.Results> getFireteamsList() {
        return fireteamsList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

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
        @SerializedName("results")
        private List<Results> resultsList;
        @Expose
        @SerializedName("totalResults")
        private String totalResults;
        @Expose
        @SerializedName("hasMore")
        private boolean hasMore;
        @Expose
        @SerializedName("query")
        private Query query;
        @Expose
        @SerializedName("useTotalResults")
        private boolean useTotalResults;

        public List<Results> getResultsList() {
            return resultsList;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public Query getQuery() {
            return query;
        }

        public boolean useTotalResults() {
            return useTotalResults;
        }

        public static class Results {
            @Expose
            @SerializedName("fireteamId")
            private String fireteamId;
            @Expose
            @SerializedName("platform")
            private int platform;
            @Expose
            @SerializedName("activityType")
            private int activityType;
            @Expose
            @SerializedName("isImmediate")
            private boolean isImmediate;
            @Expose
            @SerializedName("ownerMembershipId")
            private String ownerMembershipId;
            @Expose
            @SerializedName("playerSlotCount")
            private int playerSlotCount;
            @Expose
            @SerializedName("availablePlayerSlotCount")
            private int availablePlayerSlotCount;
            @Expose
            @SerializedName("availableAlternateSlotCount")
            private int availableAlternateSlotCount;
            @Expose
            @SerializedName("title")
            private String title;
            @Expose
            @SerializedName("dateCreated")
            private String dateCreated;
            @Expose
            @SerializedName("isPublic")
            private boolean isPublic;
            @Expose
            @SerializedName("locale")
            private String locale;
            @Expose
            @SerializedName("isValid")
            private boolean isValid;
            @Expose
            @SerializedName("datePlayerModified")
            private String datePlayerModified;

            public String getFireteamId() {
                return fireteamId;
            }

            public int getPlatform() {
                return platform;
            }

            public int getActivityType() {
                return activityType;
            }

            public boolean isImmediate() {
                return isImmediate;
            }

            public String getOwnerMembershipId() {
                return ownerMembershipId;
            }

            public int getPlayerSlotCount() {
                return playerSlotCount;
            }

            public int getAvailablePlayerSlotCount() {
                return availablePlayerSlotCount;
            }

            public int getAvailableAlternateSlotCount() {
                return availableAlternateSlotCount;
            }

            public String getTitle() {
                return title;
            }

            public String getDateCreated() {
                return dateCreated;
            }

            public boolean isPublic() {
                return isPublic;
            }

            public String getLocale() {
                return locale;
            }

            public boolean isValid() {
                return isValid;
            }

            public String getDatePlayerModified() {
                return datePlayerModified;
            }
        }

        public static class Query {
            @Expose
            @SerializedName("itemsPerPage")
            private int itemsPerPage;
            @Expose
            @SerializedName("currentPage")
            private int currentPage;

            public int getItemsPerPage() {
                return itemsPerPage;
            }

            public int getCurrentPage() {
                return currentPage;
            }
        }
    }
}

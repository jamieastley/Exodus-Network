package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Response_GetProfileCollections {


    @Expose
    @SerializedName("Response")
    private Response response;
    @Expose
    @SerializedName("ErrorCode")
    private int errorCode;
    @Expose
    @SerializedName("Message")
    private String message;

    public Response getResponse() {
        return response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static class Response {
        @Expose
        @SerializedName("profileCollectibles")
        private ProfileCollectibles profileCollectibles;

        public ProfileCollectibles getProfileCollectibles() {
            return profileCollectibles;
        }

        public static class ProfileCollectibles {
            @Expose
            @SerializedName("data")
            private Data data;
            @Expose
            @SerializedName("privacy")
            private int privacy;

            public Data getData() {
                return data;
            }

            public int getPrivacy() {
                return privacy;
            }

            public static class Data {
                @Expose
                @SerializedName("recentCollectibleHashes")
                private List<String> recentCollectibleHashList;
                @Expose
                @SerializedName("newnessFlaggedCollectibleHashes")
                private List<String> newnessFlaggedCollectibleHashes;
                @Expose
                @SerializedName("collectibles")
                private Map<String, Collectibles> collectibles;

                public List<String> getRecentCollectibleHashList() {
                    return recentCollectibleHashList;
                }

                public List<String> getNewnessFlaggedCollectibleHashes() {
                    return newnessFlaggedCollectibleHashes;
                }

                public Map<String, Collectibles> getCollectibles() {
                    return collectibles;
                }

                public static class Collectibles {
                    @Expose
                    @SerializedName("state")
                    private int state;

                    private int listPosition;
                    private boolean isAcquired;

                    public int getState() {
                        return state;
                    }

                    public int getListPosition() {
                        return listPosition;
                    }

                    public void setListPosition(int listPosition) {
                        this.listPosition = listPosition;
                    }

                    public boolean isAcquired() {
                        return isAcquired;
                    }

                    public void setAcquired(boolean acquired) {
                        isAcquired = acquired;
                    }
                }
            }
        }
    }
}

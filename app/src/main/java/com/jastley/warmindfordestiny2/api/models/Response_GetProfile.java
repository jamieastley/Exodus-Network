package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamie on 21/3/18.
 */

public class Response_GetProfile {


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
    private int ErrorCode;
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

    public int getErrorCode() {
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
        @SerializedName("profile")
        private Profile profile;

        public ItemComponents getItemComponents() {
            return itemComponents;
        }

        public Profile getProfile() {
            return profile;
        }
    }

    public static class ItemComponents {
    }

    public static class Profile {
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
        @SerializedName("characterIds")
        private List<String> characterIds;
        @Expose
        @SerializedName("versionsOwned")
        private int versionsOwned;
        @Expose
        @SerializedName("dateLastPlayed")
        private String dateLastPlayed;
        @Expose
        @SerializedName("userInfo")
        private UserInfo userInfo;

        public List<String> getCharacterIds() {
            return characterIds;
        }

        public int getVersionsOwned() {
            return versionsOwned;
        }

        public String getDateLastPlayed() {
            return dateLastPlayed;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }
    }

    public static class UserInfo {
        @Expose
        @SerializedName("displayName")
        private String displayName;
        @Expose
        @SerializedName("membershipId")
        private String membershipId;
        @Expose
        @SerializedName("membershipType")
        private int membershipType;

        public String getDisplayName() {
            return displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public int getMembershipType() {
            return membershipType;
        }
    }
}

package com.jastley.warmindfordestiny2.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamie on 21/3/18.
 */

public class Response_GetCurrentUser {


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
        @SerializedName("bungieNetUser")
        private BungieNetUser bungieNetUser;
        @Expose
        @SerializedName("destinyMemberships")
        private List<DestinyMemberships> destinyMemberships;

        public BungieNetUser getBungieNetUser() {
            return bungieNetUser;
        }

        public List<DestinyMemberships> getDestinyMemberships() {
            return destinyMemberships;
        }
    }

    public static class BungieNetUser {
        @Expose
        @SerializedName("blizzardDisplayName")
        private String blizzardDisplayName;
        @Expose
        @SerializedName("statusDate")
        private String statusDate;
        @Expose
        @SerializedName("statusText")
        private String statusText;
        @Expose
        @SerializedName("userTitleDisplay")
        private String userTitleDisplay;
        @Expose
        @SerializedName("profileThemeName")
        private String profileThemeName;
        @Expose
        @SerializedName("profilePicturePath")
        private String profilePicturePath;
        @Expose
        @SerializedName("showGroupMessaging")
        private boolean showGroupMessaging;
        @Expose
        @SerializedName("localeInheritDefault")
        private boolean localeInheritDefault;
        @Expose
        @SerializedName("locale")
        private String locale;
        @Expose
        @SerializedName("showActivity")
        private boolean showActivity;
        @Expose
        @SerializedName("psnDisplayName")
        private String psnDisplayName;
        @Expose
        @SerializedName("context")
        private Context context;
        @Expose
        @SerializedName("lastUpdate")
        private String lastUpdate;
        @Expose
        @SerializedName("firstAccess")
        private String firstAccess;
        @Expose
        @SerializedName("about")
        private String about;
        @Expose
        @SerializedName("isDeleted")
        private boolean isDeleted;
        @Expose
        @SerializedName("successMessageFlags")
        private String successMessageFlags;
        @Expose
        @SerializedName("userTitle")
        private int userTitle;
        @Expose
        @SerializedName("profileTheme")
        private int profileTheme;
        @Expose
        @SerializedName("profilePicture")
        private int profilePicture;
        @Expose
        @SerializedName("displayName")
        private String displayName;
        @Expose
        @SerializedName("uniqueName")
        private String uniqueName;
        @Expose
        @SerializedName("membershipId")
        private String membershipId;

        public String getBlizzardDisplayName() {
            return blizzardDisplayName;
        }

        public String getStatusDate() {
            return statusDate;
        }

        public String getStatusText() {
            return statusText;
        }

        public String getUserTitleDisplay() {
            return userTitleDisplay;
        }

        public String getProfileThemeName() {
            return profileThemeName;
        }

        public String getProfilePicturePath() {
            return profilePicturePath;
        }

        public boolean getShowGroupMessaging() {
            return showGroupMessaging;
        }

        public boolean getLocaleInheritDefault() {
            return localeInheritDefault;
        }

        public String getLocale() {
            return locale;
        }

        public boolean getShowActivity() {
            return showActivity;
        }

        public String getPsnDisplayName() {
            return psnDisplayName;
        }

        public Context getContext() {
            return context;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public String getFirstAccess() {
            return firstAccess;
        }

        public String getAbout() {
            return about;
        }

        public boolean getIsDeleted() {
            return isDeleted;
        }

        public String getSuccessMessageFlags() {
            return successMessageFlags;
        }

        public int getUserTitle() {
            return userTitle;
        }

        public int getProfileTheme() {
            return profileTheme;
        }

        public int getProfilePicture() {
            return profilePicture;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getUniqueName() {
            return uniqueName;
        }

        public String getMembershipId() {
            return membershipId;
        }
    }

    public static class Context {
        @Expose
        @SerializedName("ignoreStatus")
        private IgnoreStatus ignoreStatus;
        @Expose
        @SerializedName("isFollowing")
        private boolean isFollowing;

        public IgnoreStatus getIgnoreStatus() {
            return ignoreStatus;
        }

        public boolean getIsFollowing() {
            return isFollowing;
        }
    }

    public static class IgnoreStatus {
        @Expose
        @SerializedName("ignoreFlags")
        private int ignoreFlags;
        @Expose
        @SerializedName("isIgnored")
        private boolean isIgnored;

        public int getIgnoreFlags() {
            return ignoreFlags;
        }

        public boolean getIsIgnored() {
            return isIgnored;
        }
    }

    public static class DestinyMemberships {
        @Expose
        @SerializedName("displayName")
        private String displayName;
        @Expose
        @SerializedName("membershipId")
        private String membershipId;
        @Expose
        @SerializedName("membershipType")
        private int membershipType;
        @Expose
        @SerializedName("iconPath")
        private String iconPath;

        public String getDisplayName() {
            return displayName;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public int getMembershipType() {
            return membershipType;
        }

        public String getIconPath() {
            return iconPath;
        }
    }
}

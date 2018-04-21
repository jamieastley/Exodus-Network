package com.jastley.warmindfordestiny2.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamie1192 on 21/4/18.
 */

public class Response_GetGroupsForMember {

    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("ErrorCode")
    @Expose
    private int errorCode;
    @SerializedName("ThrottleSeconds")
    @Expose
    private int throttleSeconds;
    @SerializedName("ErrorStatus")
    @Expose
    private String errorStatus;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("MessageData")
    @Expose
    private MessageData messageData;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getThrottleSeconds() {
        return throttleSeconds;
    }

    public void setThrottleSeconds(int throttleSeconds) {
        this.throttleSeconds = throttleSeconds;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public static class Response {

        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        @SerializedName("totalResults")
        @Expose
        private int totalResults;
        @SerializedName("hasMore")
        @Expose
        private boolean hasMore;
        @SerializedName("query")
        @Expose
        private Query query;
        @SerializedName("useTotalResults")
        @Expose
        private boolean useTotalResults;
        private final static long serialVersionUID = 7448940307766995494L;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }

        public boolean isUseTotalResults() {
            return useTotalResults;
        }

        public void setUseTotalResults(boolean useTotalResults) {
            this.useTotalResults = useTotalResults;
        }
    }

    public static class Result {

        @SerializedName("member")
        @Expose
        private Member member;
        @SerializedName("group")
        @Expose
        private Group group;
        private final static long serialVersionUID = -6923648532653988487L;

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }
    }

    public static class Query {

        @SerializedName("itemsPerPage")
        @Expose
        private int itemsPerPage;
        @SerializedName("currentPage")
        @Expose
        private int currentPage;
        private final static long serialVersionUID = 3704109791312607236L;

        public int getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class MessageData {

    }

    public static class Member {

        @SerializedName("memberType")
        @Expose
        private int memberType;
        @SerializedName("isOnline")
        @Expose
        private boolean isOnline;
        @SerializedName("groupId")
        @Expose
        private String groupId;
        @SerializedName("destinyUserInfo")
        @Expose
        private DestinyUserInfo destinyUserInfo;
        @SerializedName("joinDate")
        @Expose
        private String joinDate;
        private final static long serialVersionUID = 5081068817878655100L;

        public int getMemberType() {
            return memberType;
        }

        public void setMemberType(int memberType) {
            this.memberType = memberType;
        }

        public boolean isIsOnline() {
            return isOnline;
        }

        public void setIsOnline(boolean isOnline) {
            this.isOnline = isOnline;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public DestinyUserInfo getDestinyUserInfo() {
            return destinyUserInfo;
        }

        public void setDestinyUserInfo(DestinyUserInfo destinyUserInfo) {
            this.destinyUserInfo = destinyUserInfo;
        }

        public String getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(String joinDate) {
            this.joinDate = joinDate;
        }
    }

    public static class Group {

        @SerializedName("groupId")
        @Expose
        private String groupId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("groupType")
        @Expose
        private int groupType;
        @SerializedName("membershipIdCreated")
        @Expose
        private String membershipIdCreated;
        @SerializedName("creationDate")
        @Expose
        private String creationDate;
        @SerializedName("modificationDate")
        @Expose
        private String modificationDate;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("tags")
        @Expose
        private List<Object> tags = null;
        @SerializedName("memberCount")
        @Expose
        private int memberCount;
        @SerializedName("isPublic")
        @Expose
        private boolean isPublic;
        @SerializedName("isPublicTopicAdminOnly")
        @Expose
        private boolean isPublicTopicAdminOnly;
        @SerializedName("motto")
        @Expose
        private String motto;
        @SerializedName("allowChat")
        @Expose
        private boolean allowChat;
        @SerializedName("isDefaultPostPublic")
        @Expose
        private boolean isDefaultPostPublic;
        @SerializedName("chatSecurity")
        @Expose
        private int chatSecurity;
        @SerializedName("locale")
        @Expose
        private String locale;
        @SerializedName("avatarImageIndex")
        @Expose
        private int avatarImageIndex;
        @SerializedName("homepage")
        @Expose
        private int homepage;
        @SerializedName("membershipOption")
        @Expose
        private int membershipOption;
        @SerializedName("defaultPublicity")
        @Expose
        private int defaultPublicity;
        @SerializedName("theme")
        @Expose
        private String theme;
        @SerializedName("bannerPath")
        @Expose
        private String bannerPath;
        @SerializedName("avatarPath")
        @Expose
        private String avatarPath;
        @SerializedName("conversationId")
        @Expose
        private String conversationId;
        @SerializedName("enableInvitationMessagingForAdmins")
        @Expose
        private boolean enableInvitationMessagingForAdmins;
        @SerializedName("banExpireDate")
        @Expose
        private String banExpireDate;
        @SerializedName("features")
        @Expose
        private Features features;
        @SerializedName("clanInfo")
        @Expose
        private ClanInfo clanInfo;
        private final static long serialVersionUID = 7204127239846331417L;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
        }

        public String getMembershipIdCreated() {
            return membershipIdCreated;
        }

        public void setMembershipIdCreated(String membershipIdCreated) {
            this.membershipIdCreated = membershipIdCreated;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getModificationDate() {
            return modificationDate;
        }

        public void setModificationDate(String modificationDate) {
            this.modificationDate = modificationDate;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public List<Object> getTags() {
            return tags;
        }

        public void setTags(List<Object> tags) {
            this.tags = tags;
        }

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public boolean isIsPublic() {
            return isPublic;
        }

        public void setIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }

        public boolean isIsPublicTopicAdminOnly() {
            return isPublicTopicAdminOnly;
        }

        public void setIsPublicTopicAdminOnly(boolean isPublicTopicAdminOnly) {
            this.isPublicTopicAdminOnly = isPublicTopicAdminOnly;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public boolean isAllowChat() {
            return allowChat;
        }

        public void setAllowChat(boolean allowChat) {
            this.allowChat = allowChat;
        }

        public boolean isIsDefaultPostPublic() {
            return isDefaultPostPublic;
        }

        public void setIsDefaultPostPublic(boolean isDefaultPostPublic) {
            this.isDefaultPostPublic = isDefaultPostPublic;
        }

        public int getChatSecurity() {
            return chatSecurity;
        }

        public void setChatSecurity(int chatSecurity) {
            this.chatSecurity = chatSecurity;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public int getAvatarImageIndex() {
            return avatarImageIndex;
        }

        public void setAvatarImageIndex(int avatarImageIndex) {
            this.avatarImageIndex = avatarImageIndex;
        }

        public int getHomepage() {
            return homepage;
        }

        public void setHomepage(int homepage) {
            this.homepage = homepage;
        }

        public int getMembershipOption() {
            return membershipOption;
        }

        public void setMembershipOption(int membershipOption) {
            this.membershipOption = membershipOption;
        }

        public int getDefaultPublicity() {
            return defaultPublicity;
        }

        public void setDefaultPublicity(int defaultPublicity) {
            this.defaultPublicity = defaultPublicity;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getBannerPath() {
            return bannerPath;
        }

        public void setBannerPath(String bannerPath) {
            this.bannerPath = bannerPath;
        }

        public String getAvatarPath() {
            return avatarPath;
        }

        public void setAvatarPath(String avatarPath) {
            this.avatarPath = avatarPath;
        }

        public String getConversationId() {
            return conversationId;
        }

        public void setConversationId(String conversationId) {
            this.conversationId = conversationId;
        }

        public boolean isEnableInvitationMessagingForAdmins() {
            return enableInvitationMessagingForAdmins;
        }

        public void setEnableInvitationMessagingForAdmins(boolean enableInvitationMessagingForAdmins) {
            this.enableInvitationMessagingForAdmins = enableInvitationMessagingForAdmins;
        }

        public String getBanExpireDate() {
            return banExpireDate;
        }

        public void setBanExpireDate(String banExpireDate) {
            this.banExpireDate = banExpireDate;
        }

        public Features getFeatures() {
            return features;
        }

        public void setFeatures(Features features) {
            this.features = features;
        }

        public ClanInfo getClanInfo() {
            return clanInfo;
        }

        public void setClanInfo(ClanInfo clanInfo) {
            this.clanInfo = clanInfo;
        }
    }

    public static class GetGroupsForMember {

        @SerializedName("Response")
        @Expose
        private Response response;
        @SerializedName("ErrorCode")
        @Expose
        private int errorCode;
        @SerializedName("ThrottleSeconds")
        @Expose
        private int throttleSeconds;
        @SerializedName("ErrorStatus")
        @Expose
        private String errorStatus;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("MessageData")
        @Expose
        private MessageData messageData;
        private final static long serialVersionUID = -372952757827834859L;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getThrottleSeconds() {
            return throttleSeconds;
        }

        public void setThrottleSeconds(int throttleSeconds) {
            this.throttleSeconds = throttleSeconds;
        }

        public String getErrorStatus() {
            return errorStatus;
        }

        public void setErrorStatus(String errorStatus) {
            this.errorStatus = errorStatus;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public MessageData getMessageData() {
            return messageData;
        }

        public void setMessageData(MessageData messageData) {
            this.messageData = messageData;
        }
    }

    public static class Features {

        @SerializedName("maximumMembers")
        @Expose
        private int maximumMembers;
        @SerializedName("maximumMembershipsOfGroupType")
        @Expose
        private int maximumMembershipsOfGroupType;
        @SerializedName("capabilities")
        @Expose
        private int capabilities;
        @SerializedName("membershipTypes")
        @Expose
        private List<Integer> membershipTypes = null;
        @SerializedName("invitePermissionOverride")
        @Expose
        private boolean invitePermissionOverride;
        @SerializedName("updateCulturePermissionOverride")
        @Expose
        private boolean updateCulturePermissionOverride;
        @SerializedName("hostGuidedGamePermissionOverride")
        @Expose
        private int hostGuidedGamePermissionOverride;
        @SerializedName("updateBannerPermissionOverride")
        @Expose
        private boolean updateBannerPermissionOverride;
        @SerializedName("joinLevel")
        @Expose
        private int joinLevel;
        private final static long serialVersionUID = -5837803668080538294L;

        public int getMaximumMembers() {
            return maximumMembers;
        }

        public void setMaximumMembers(int maximumMembers) {
            this.maximumMembers = maximumMembers;
        }

        public int getMaximumMembershipsOfGroupType() {
            return maximumMembershipsOfGroupType;
        }

        public void setMaximumMembershipsOfGroupType(int maximumMembershipsOfGroupType) {
            this.maximumMembershipsOfGroupType = maximumMembershipsOfGroupType;
        }

        public int getCapabilities() {
            return capabilities;
        }

        public void setCapabilities(int capabilities) {
            this.capabilities = capabilities;
        }

        public List<Integer> getMembershipTypes() {
            return membershipTypes;
        }

        public void setMembershipTypes(List<Integer> membershipTypes) {
            this.membershipTypes = membershipTypes;
        }

        public boolean isInvitePermissionOverride() {
            return invitePermissionOverride;
        }

        public void setInvitePermissionOverride(boolean invitePermissionOverride) {
            this.invitePermissionOverride = invitePermissionOverride;
        }

        public boolean isUpdateCulturePermissionOverride() {
            return updateCulturePermissionOverride;
        }

        public void setUpdateCulturePermissionOverride(boolean updateCulturePermissionOverride) {
            this.updateCulturePermissionOverride = updateCulturePermissionOverride;
        }

        public int getHostGuidedGamePermissionOverride() {
            return hostGuidedGamePermissionOverride;
        }

        public void setHostGuidedGamePermissionOverride(int hostGuidedGamePermissionOverride) {
            this.hostGuidedGamePermissionOverride = hostGuidedGamePermissionOverride;
        }

        public boolean isUpdateBannerPermissionOverride() {
            return updateBannerPermissionOverride;
        }

        public void setUpdateBannerPermissionOverride(boolean updateBannerPermissionOverride) {
            this.updateBannerPermissionOverride = updateBannerPermissionOverride;
        }

        public int getJoinLevel() {
            return joinLevel;
        }

        public void setJoinLevel(int joinLevel) {
            this.joinLevel = joinLevel;
        }
    }

    public static class DestinyUserInfo {

        @SerializedName("iconPath")
        @Expose
        private String iconPath;
        @SerializedName("membershipType")
        @Expose
        private int membershipType;
        @SerializedName("membershipId")
        @Expose
        private String membershipId;
        @SerializedName("displayName")
        @Expose
        private String displayName;
        private final static long serialVersionUID = 9120437317445082212L;

        public String getIconPath() {
            return iconPath;
        }

        public void setIconPath(String iconPath) {
            this.iconPath = iconPath;
        }

        public int getMembershipType() {
            return membershipType;
        }

        public void setMembershipType(int membershipType) {
            this.membershipType = membershipType;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String membershipId) {
            this.membershipId = membershipId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class ClanInfo {

        @SerializedName("clanCallsign")
        @Expose
        private String clanCallsign;

        public String getClanCallsign() {
            return clanCallsign;
        }

        public void setClanCallsign(String clanCallsign) {
            this.clanCallsign = clanCallsign;
        }


    }


}

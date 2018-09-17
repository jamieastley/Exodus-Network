package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_GetBungieManifest {

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

    public static class Response{
        @Expose
        @SerializedName("version")
        private String version;
        @Expose
        @SerializedName("mobileWorldContentPaths")
        private MobileWorldContentPaths mobileWorldContentPaths;

        public String getVersion() {
            return version;
        }

        public MobileWorldContentPaths getMobileWorldContentPaths() {
            return mobileWorldContentPaths;
        }
    }

    public static class MobileWorldContentPaths {
        @Expose
        @SerializedName("en")
        private String englishPath;

        public String getEnglishPath() {
            return englishPath;
        }
    }
}

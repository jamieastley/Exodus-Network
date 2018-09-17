package com.jastley.exodusnetwork.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_DestinyPlumbing {


    @Expose
    @SerializedName("bungieManifestVersion")
    private String bungieManifestVersion;
    @Expose
    @SerializedName("lastUpdated")
    private String lastUpdated;
    @Expose
    @SerializedName("id")
    private String id;

    public String getBungieManifestVersion() {
        return bungieManifestVersion;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getId() {
        return id;
    }
}

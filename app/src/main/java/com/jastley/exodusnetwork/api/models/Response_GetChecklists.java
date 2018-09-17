package com.jastley.exodusnetwork.api.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jastley.exodusnetwork.checklists.models.ChecklistModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response_GetChecklists {

    private List<ChecklistModel> checklistModelList = new ArrayList<>();
    private String errorMessage;
    private Throwable throwable;

    public Response_GetChecklists(String errorMessage) {
        this.errorMessage = errorMessage;
        this.throwable = null;
    }

    public Response_GetChecklists(Throwable throwable) {
        this.errorMessage = null;
        this.throwable = throwable;
    }

    public Response_GetChecklists(List<ChecklistModel> checklistModelList) {
        this.checklistModelList = checklistModelList;
    }

    public List<ChecklistModel> getChecklistModelList() {
        return checklistModelList;
    }

    public void setChecklistModelList(List<ChecklistModel> checklistModelList) {
        this.checklistModelList = checklistModelList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }


    @Expose
    @SerializedName("Response")
    private Response response;

    @Expose
    @SerializedName("ErrorCode")
    private int errorCode;

    @Expose
    @SerializedName("Message")
    private String message;

    @Expose
    @SerializedName("ErrorStatus")
    private String errorStatus;

    public Response getResponse() {
        return response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public static class Response {
        @Expose
        @SerializedName("profileProgression")
        private ProfileProgression profileProgression;
        @Expose
        @SerializedName("itemComponents")
        private ItemComponents itemComponents;

        public ProfileProgression getProfileProgression() {
            return profileProgression;
        }

        public ItemComponents getItemComponents() {
            return itemComponents;
        }

        public static class ProfileProgression {
            @Expose
            @SerializedName("data")
            private ProgressionData data;
            @Expose
            @SerializedName("privacy")
            private int privacy;

            public ProgressionData getData() {
                return data;
            }

            public int getPrivacy() {
                return privacy;
            }

            public static class ProgressionData {
                @Expose
                @SerializedName("checklists")
                private Map<String, JsonObject> checklistDefinition;

                public Map<String, JsonObject> getChecklistDefinition() {
                    return checklistDefinition;
                }
            }
        }

        public static class ItemComponents {

        }
    }


}

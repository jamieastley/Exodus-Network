package com.jastley.exodusnetwork.onboarding;

public class DownloadProgressModel {

    private Long progress;
    private Long fileSize;
    private String message;
    private boolean isComplete;

    public DownloadProgressModel(Long progress, Long fileSize, String message) {
        this.progress = progress;
        this.fileSize = fileSize;
        this.message = message;
    }

    public DownloadProgressModel(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}

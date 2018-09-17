package com.jastley.exodusnetwork.onboarding;

public class DownloadProgressModel {

    private int progress;
    private int fileSize;
    private String message;
    private boolean isComplete;
    private Throwable throwable;
    private String errorMessage;

    public DownloadProgressModel(int progress, int fileSize, String message) {
        this.progress = progress;
        this.fileSize = fileSize;
        this.message = message;
    }

    public DownloadProgressModel(boolean isComplete) {
        this.isComplete = isComplete;
        this.throwable = null;
        this.errorMessage = null;
    }

    public DownloadProgressModel(Throwable error) {
        this.throwable = error;
    }

    public DownloadProgressModel(String errorMessage) {
        this.errorMessage = errorMessage;
        this.throwable = null;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
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

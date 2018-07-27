package com.jastley.exodusnetwork.onboarding;

public class DownloadProgressModel {

    private Long progress;
    private Long fileSize;
    private String message;

    public DownloadProgressModel(Long progress, Long fileSize, String message) {
        this.progress = progress;
        this.fileSize = fileSize;
        this.message = message;
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
}

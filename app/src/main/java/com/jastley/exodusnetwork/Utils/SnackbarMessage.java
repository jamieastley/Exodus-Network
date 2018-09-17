package com.jastley.exodusnetwork.Utils;

public class SnackbarMessage {

    private String message;
    private Throwable throwable;

    public SnackbarMessage(String message) {
        this.message = message;
    }

    public SnackbarMessage(Throwable throwable) {
        this.message = null;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}

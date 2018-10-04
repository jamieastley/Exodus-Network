package com.jastley.exodusnetwork.Utils;

import java.util.ArrayList;
import java.util.List;

public class LiveDataResponseModel {

    private String message;
    private Throwable throwable;
    private List<?> itemList;

    public LiveDataResponseModel(String message) {
        this.message = message;
    }

    public LiveDataResponseModel(Throwable throwable) {
        this.throwable = throwable;
    }

    public LiveDataResponseModel(List<?> itemList) {
        this.itemList = itemList;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public List<?> getItemList() {
        return itemList;
    }
}

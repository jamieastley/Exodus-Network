package com.jastley.exodusnetwork.Vendors.models;

import java.util.List;

public class SocketModel {

    private String socketName;
    private String socketIcon;
    private String socketDescription;
    private int socketIndex;
    private String plugItemHash;
    private List<SocketModel> socketModelList;

    //LiveData error checking
    private Throwable throwable;

    public SocketModel() {

    }

    public SocketModel(List<SocketModel> socketModelList) {
        this.socketModelList = socketModelList;
        this.throwable = null;
    }

    public SocketModel(Throwable throwable) {
        this.throwable = throwable;
        this.socketModelList = null;
    }

    public String getSocketName() {
        return socketName;
    }

    public void setSocketName(String socketName) {
        this.socketName = socketName;
    }

    public String getSocketIcon() {
        return socketIcon;
    }

    public void setSocketIcon(String socketIcon) {
        this.socketIcon = socketIcon;
    }

    public int getSocketIndex() {
        return socketIndex;
    }

    public void setSocketIndex(int socketIndex) {
        this.socketIndex = socketIndex;
    }

    public String getPlugItemHash() {
        return plugItemHash;
    }

    public void setPlugItemHash(String plugItemHash) {
        this.plugItemHash = plugItemHash;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public List<SocketModel> getSocketModelList() {
        return socketModelList;
    }

    public void setSocketModelList(List<SocketModel> socketModelList) {
        this.socketModelList = socketModelList;
    }

    public String getSocketDescription() {
        return socketDescription;
    }

    public void setSocketDescription(String socketDescription) {
        this.socketDescription = socketDescription;
    }
}

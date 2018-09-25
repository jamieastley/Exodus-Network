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

    public static class StatValues {

        private String statTypeHash;
        private int value;
        private int maximum;
        private int position;
        private String statName;

        private String error;
        private Throwable throwable;
        private List<StatValues> statList;

        //Initial constructor
        public StatValues(String statTypeHash, int value, int max, int position) {
            this.statTypeHash = statTypeHash;
            this.value = value;
            this.maximum = max;
            this.position = position;
        }

        //After extra data append
        public StatValues(List<StatValues> statList) {
            this.statList = statList;
        }

        //Error retrieving extra data
        public StatValues(Throwable throwable) {
            this.throwable = throwable;
        }
        //Error message
        public StatValues(String error) {
            this.error = error;
        }

        public String getStatTypeHash() {
            return statTypeHash;
        }

        public void setStatTypeHash(String statTypeHash) {
            this.statTypeHash = statTypeHash;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getMaximum() {
            return maximum;
        }

        public void setMaximum(int maximum) {
            this.maximum = maximum;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getStatName() {
            return statName;
        }

        public void setStatName(String statName) {
            this.statName = statName;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public List<StatValues> getStatList() {
            return statList;
        }

        public String getError() {
            return error;
        }
    }
}

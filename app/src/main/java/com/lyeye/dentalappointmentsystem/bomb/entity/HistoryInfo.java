package com.lyeye.dentalappointmentsystem.bomb.entity;

public class HistoryInfo {
    private int historyId;
    private int userId;
    private int amiId;
    private int symptomId;

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAmiId() {
        return amiId;
    }

    public void setAmiId(int amiId) {
        this.amiId = amiId;
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }
}

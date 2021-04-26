package com.lyeye.dentalappointmentsystem.bomb.entity;

public class RemoteInfo {
    private int amiId;
    private int userId;
    private int hospitalId;
    private String date;
    private String time;
    private boolean isFinish;

    public int getAmiId() {
        return amiId;
    }

    public void setAmiId(int amiId) {
        this.amiId = amiId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}

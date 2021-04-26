package com.lyeye.dentalappointmentsystem.bomb.entity;

import java.sql.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class AppointmentInfo extends BmobObject {
    private int amiId;
    private int userId;
    private int hospitalId;
    private String date;
    private String time;
    private int symptomId;
    private boolean isRemote;
    private boolean isArrive;

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

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public boolean isArrive() {
        return isArrive;
    }

    public void setArrive(boolean arrive) {
        isArrive = arrive;
    }
}

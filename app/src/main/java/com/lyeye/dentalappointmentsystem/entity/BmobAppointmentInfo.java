package com.lyeye.dentalappointmentsystem.entity;

import cn.bmob.v3.BmobObject;

public class BmobAppointmentInfo extends BmobObject {

    private String userName;

    private String amiDate;

    private String amiTime;

    private String amiSymptoms = "请选择牙齿疾病类型";

    public BmobAppointmentInfo() {
    }

    public BmobAppointmentInfo(String userName, String amiDate, String amiTime, String amiSymptoms) {
        this.userName = userName;
        this.amiDate = amiDate;
        this.amiTime = amiTime;
        this.amiSymptoms = amiSymptoms;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmiDate() {
        return amiDate;
    }

    public void setAmiDate(String amiDate) {
        this.amiDate = amiDate;
    }

    public String getAmiTime() {
        return amiTime;
    }

    public void setAmiTime(String amiTime) {
        this.amiTime = amiTime;
    }

    public String getAmiSymptoms() {
        return amiSymptoms;
    }

    public void setAmiSymptoms(String amiSymptoms) {
        this.amiSymptoms = amiSymptoms;
    }

    @Override
    public String toString() {
        return "BmobAppointmentInfo{" +
                "userName='" + userName + '\'' +
                ", amiDate='" + amiDate + '\'' +
                ", amiTime='" + amiTime + '\'' +
                ", amiSymptoms='" + amiSymptoms + '\'' +
                '}';
    }
}

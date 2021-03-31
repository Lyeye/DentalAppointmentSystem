package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class AppointmentInfo {

    @Id(autoincrement = true)
    private Long amiId;

    @Property(nameInDb = "USER_NAME")
    @NotNull
    private String userName;

    @Property(nameInDb = "APPOINTMENT_INFO_TIME")
    private String amiTime;

    @Property(nameInDb = "APPOINTMENT_INFO_DOCTOR")
    private String amiDoctor;

    @Property(nameInDb = "APPOINTMENT_INFO_SYMPTOMS")
    private String amiSymptoms;


    @Generated(hash = 1142461733)
    public AppointmentInfo() {
    }

    @Generated(hash = 1187571826)
    public AppointmentInfo(Long amiId, @NotNull String userName, String amiTime,
                           String amiDoctor, String amiSymptoms) {
        this.amiId = amiId;
        this.userName = userName;
        this.amiTime = amiTime;
        this.amiDoctor = amiDoctor;
        this.amiSymptoms = amiSymptoms;
    }

    public Long getAmiId() {
        return this.amiId;
    }

    public void setAmiId(Long amiId) {
        this.amiId = amiId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmiTime() {
        return this.amiTime;
    }

    public void setAmiTime(String amiTime) {
        this.amiTime = amiTime;
    }

    public String getAmiSymptoms() {
        return this.amiSymptoms;
    }

    public void setAmiSymptoms(String amiSymptoms) {
        this.amiSymptoms = amiSymptoms;
    }

    public String getAmiDoctor() {
        return this.amiDoctor;
    }

    public void setAmiDoctor(String amiDoctor) {
        this.amiDoctor = amiDoctor;
    }

}

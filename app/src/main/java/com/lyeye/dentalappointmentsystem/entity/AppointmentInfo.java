package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity
public class AppointmentInfo {

    @Id(autoincrement = true)
    @Property(nameInDb = "APPOINTMENT_INFO_ID")
    private Long amiId;

    @Property(nameInDb = "USER_NAME")
    @NotNull
    private long userId;

    @Property(nameInDb = "AFFLILIATED_HOSPITAL")
    private String affiliatedHospital;

    @Property(nameInDb = "APPOINTMENT_INFO_DATE")
    private String amiDate;

    @Property(nameInDb = "APPOINTMENT_INFO_TIME")
    private String amiTime;

    @Property(nameInDb = "APPOINTMENT_INFO_SYMPTOMS")
    private String amiSymptoms;

    @Property(nameInDb = "CREATE_AT")
    private Date createAt;

    @Generated(hash = 1142461733)
    public AppointmentInfo() {
    }


    @Generated(hash = 751813133)
    public AppointmentInfo(Long amiId, long userId, String affiliatedHospital,
                           String amiDate, String amiTime, String amiSymptoms, Date createAt) {
        this.amiId = amiId;
        this.userId = userId;
        this.affiliatedHospital = affiliatedHospital;
        this.amiDate = amiDate;
        this.amiTime = amiTime;
        this.amiSymptoms = amiSymptoms;
        this.createAt = createAt;
    }


    public Long getAmiId() {
        return this.amiId;
    }

    public void setAmiId(Long amiId) {
        this.amiId = amiId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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


    public String getAmiDate() {
        return this.amiDate;
    }


    public void setAmiDate(String amiDate) {
        this.amiDate = amiDate;
    }

    @Override
    public String toString() {
        return "AppointmentInfo{" +
                "amiId=" + amiId +
                ", userId=" + userId +
                ", affiliatedHospital='" + affiliatedHospital + '\'' +
                ", amiDate='" + amiDate + '\'' +
                ", amiTime='" + amiTime + '\'' +
                ", amiSymptoms='" + amiSymptoms + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public String getAffiliatedHospital() {
        return this.affiliatedHospital;
    }


    public void setAffiliatedHospital(String affiliatedHospital) {
        this.affiliatedHospital = affiliatedHospital;
    }


    public Date getCreateAt() {
        return this.createAt;
    }


    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

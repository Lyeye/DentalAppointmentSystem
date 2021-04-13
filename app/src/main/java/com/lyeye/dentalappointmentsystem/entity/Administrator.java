package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Administrator {

    @Id(autoincrement = true)
    @Property(nameInDb = "ADMINISTRATOR_ID")
    private Long administratorId;

    @Property(nameInDb = "ADMINISTRATOR_NAME")
    @NotNull
    private String administratorName;

    @Property(nameInDb = "ADMINISTRATOR_PASSWORD")
    private String administratorPwd;

    @Property(nameInDb = "ADMINISTRATOR_EMAIL")
    private String administratorEmail;

    @Generated(hash = 1757338798)
    public Administrator(Long administratorId, @NotNull String administratorName,
                         String administratorPwd, String administratorEmail) {
        this.administratorId = administratorId;
        this.administratorName = administratorName;
        this.administratorPwd = administratorPwd;
        this.administratorEmail = administratorEmail;
    }

    @Generated(hash = 129537203)
    public Administrator() {
    }

    public Long getAdministratorId() {
        return this.administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    public String getAdministratorName() {
        return this.administratorName;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName;
    }

    public String getAdministratorPwd() {
        return this.administratorPwd;
    }

    public void setAdministratorPwd(String administratorPwd) {
        this.administratorPwd = administratorPwd;
    }

    public String getAdministratorEmail() {
        return this.administratorEmail;
    }

    public void setAdministratorEmail(String administratorEmail) {
        this.administratorEmail = administratorEmail;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "administratorId=" + administratorId +
                ", administratorName='" + administratorName + '\'' +
                ", administratorPwd='" + administratorPwd + '\'' +
                ", administratorEmail='" + administratorEmail + '\'' +
                '}';
    }
}

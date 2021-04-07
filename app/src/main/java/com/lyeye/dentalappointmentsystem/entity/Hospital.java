package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Hospital {

    @Id(autoincrement = true)
    @Property(nameInDb = "HOSPITAL_ID")
    private Long hospitalId;

    @Property(nameInDb = "HOSPITAL_NAME")
    private String hospitalName;

    @Generated(hash = 1193576811)
    public Hospital(Long hospitalId, String hospitalName) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }

    @Generated(hash = 1301721268)
    public Hospital() {
    }

    public Long getHospitalId() {
        return this.hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                '}';
    }
}

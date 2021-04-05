package com.lyeye.dentalappointmentsystem.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity
public class User {

    @Id(autoincrement = true)
    private Long userId;

    @Property(nameInDb = "USER_NAME")
    @NotNull
    private String userName;

    @Property(nameInDb = "USER_AGE")
    private Long userAge;

    @Property(nameInDb = "USER_GENDER")
    private String userGender;

    @Property(nameInDb = "USER_PASSWORD")
    private String userPwd;

    @Property(nameInDb = "USER_EMAIL")
    private String userEmail;

    @Property(nameInDb = "USER_PHONE_NUMBER")
    private String userPhoneNumber;

    @Property(nameInDb = "AFFLILIATED_HOSPITAL")
    private String affiliatedHospital;

    @Property(nameInDb = "USER_BIRTHDAY")
    private Date userBirthday;

    @Property(nameInDb = "DIAGNOSIS_NUMBER")
    private String diagnosisNumber;


    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 1491873404)
    public User(Long userId, @NotNull String userName, Long userAge,
                String userGender, String userPwd, String userEmail,
                String userPhoneNumber, String affiliatedHospital, Date userBirthday,
                String diagnosisNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.affiliatedHospital = affiliatedHospital;
        this.userBirthday = userBirthday;
        this.diagnosisNumber = diagnosisNumber;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return this.userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getAffiliatedHospital() {
        return this.affiliatedHospital;
    }

    public void setAffiliatedHospital(String affiliatedHospital) {
        this.affiliatedHospital = affiliatedHospital;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", affiliatedHospital='" + affiliatedHospital + '\'' +
                ", userBirthday=" + userBirthday +
                ", diagnosisNumber='" + diagnosisNumber + '\'' +
                '}';
    }

    public Long getUserAge() {
        return this.userAge;
    }

    public void setUserAge(Long userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return this.userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday() {
        return this.userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getDiagnosisNumber() {
        return this.diagnosisNumber;
    }

    public void setDiagnosisNumber(String diagnosisNumber) {
        this.diagnosisNumber = diagnosisNumber;
    }
}

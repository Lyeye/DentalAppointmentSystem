package com.lyeye.dentalappointmentsystem.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class BmobUser extends BmobObject {

    private String userName;

    private Long userAge;

    private String userGender;

    private String userPwd;

    private String userEmail;

    private String userPhoneNumber;

    private String affiliatedHospital;

    private Date userBirthday;

    private String diagnosisNumber;

    private BmobFile userPic;

    public BmobUser() {
    }

    public BmobUser(String userName, Long userAge, String userGender, String userPwd, String userEmail, String userPhoneNumber, String affiliatedHospital, Date userBirthday, String diagnosisNumber, BmobFile userPic) {
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.affiliatedHospital = affiliatedHospital;
        this.userBirthday = userBirthday;
        this.diagnosisNumber = diagnosisNumber;
        this.userPic = userPic;
    }

    @Override
    public String toString() {
        return "BmobUser{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", affiliatedHospital='" + affiliatedHospital + '\'' +
                ", userBirthday=" + userBirthday +
                ", diagnosisNumber='" + diagnosisNumber + '\'' +
                ", userPic=" + userPic +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserAge() {
        return userAge;
    }

    public void setUserAge(Long userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getAffiliatedHospital() {
        return affiliatedHospital;
    }

    public void setAffiliatedHospital(String affiliatedHospital) {
        this.affiliatedHospital = affiliatedHospital;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getDiagnosisNumber() {
        return diagnosisNumber;
    }

    public void setDiagnosisNumber(String diagnosisNumber) {
        this.diagnosisNumber = diagnosisNumber;
    }

    public BmobFile getUserPic() {
        return userPic;
    }

    public void setUserPic(BmobFile userPic) {
        this.userPic = userPic;
    }
}

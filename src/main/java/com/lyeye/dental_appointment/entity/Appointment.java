package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Appointment {
    private int amiId;
    private int userId;
    private int hospitalId;
    private String hospitalName;
    private String userName;
    private String symptom;
    private String date;
    private String time;
    private int isRemote;
    private int isArrive;
}

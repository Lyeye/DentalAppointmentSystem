package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Register {
    private int registerId;
    private int userId;
    private String userName;
    private String hospitalName;
    private String date;
    private String time;
}

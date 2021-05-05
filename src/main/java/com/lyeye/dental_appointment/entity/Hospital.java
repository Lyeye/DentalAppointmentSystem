package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Hospital {
    private int hospitalId;
    private String name;
    private String address;
    private String phone;
    private String doctor;
    private String holiday;

}

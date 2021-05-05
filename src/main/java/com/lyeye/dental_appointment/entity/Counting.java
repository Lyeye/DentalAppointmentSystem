package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Counting {
    private int id;
    private int hospitalId;
    private String hospital;
    private String date;
    private String time;
    private int sum;
}

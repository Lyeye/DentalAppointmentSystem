package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Inquiry {
    private int inquiryId;
    private int userId;
    private int hospitalId;
    private String userName;
    private String hospital;
    private String symptom;
    private String level;
    private int isRemote;
}

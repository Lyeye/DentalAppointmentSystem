package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Notice {
    private int id;
    private int userId;
    private String content;
    private String picture;
    private String from;
    private String date;
    private String time;
}

package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class User {
    private int userId;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String phone;
    private String number;
    private String birthday;
    private String createAt;
    private String type;
}

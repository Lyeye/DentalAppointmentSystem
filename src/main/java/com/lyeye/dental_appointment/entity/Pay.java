package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Pay {
    private int payId;
    private int userId;
    private String payer;
    private String payee;
    private int amount;
    private String date;
    private String time;
}

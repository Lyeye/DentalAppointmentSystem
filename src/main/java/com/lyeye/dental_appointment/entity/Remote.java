package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Remote {
    private int remoteId;
    private int userId;
    private String userName;
    private String roomName;
    private String date;
    private String startTime;
    private String finishTime;
}

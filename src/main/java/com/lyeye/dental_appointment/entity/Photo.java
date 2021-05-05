package com.lyeye.dental_appointment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Photo {

    private int photoId;
    private int userId;
    private String userName;
    private String path;
    private String uploadTo;
    private String uploadAt;
}

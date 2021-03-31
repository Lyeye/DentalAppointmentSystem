package com.lyeye.dentalappointmentsystem.util;

public class AppoinmentInfoMessageEvent {

    private String message;

    public AppoinmentInfoMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

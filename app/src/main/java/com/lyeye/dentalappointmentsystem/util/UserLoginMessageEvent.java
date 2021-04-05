package com.lyeye.dentalappointmentsystem.util;

import com.lyeye.dentalappointmentsystem.entity.User;

public class UserLoginMessageEvent {

    private User user;

    public UserLoginMessageEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

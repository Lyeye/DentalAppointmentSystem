package com.lyeye.dentalappointmentsystem.util;

import com.lyeye.dentalappointmentsystem.entity.BmobUser;

public class UserLoginMessageEvent {

    private com.lyeye.dentalappointmentsystem.entity.BmobUser user;

    public UserLoginMessageEvent(BmobUser user) {
        this.user = user;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }
}

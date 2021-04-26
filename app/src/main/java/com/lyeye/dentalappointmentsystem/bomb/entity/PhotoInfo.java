package com.lyeye.dentalappointmentsystem.bomb.entity;

public class PhotoInfo {
    private int userId;
    private String path;
    private boolean isUpload;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}

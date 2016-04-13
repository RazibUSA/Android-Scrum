package com.gnirt69.mscrum.model;


public class LogTime extends DataModel{


    private String lockedTime;
    private User user;

    public String getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

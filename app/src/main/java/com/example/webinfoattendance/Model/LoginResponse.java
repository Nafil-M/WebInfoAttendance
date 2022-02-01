package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("userid")
    public String userid;

    @SerializedName("status")
    public boolean status;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

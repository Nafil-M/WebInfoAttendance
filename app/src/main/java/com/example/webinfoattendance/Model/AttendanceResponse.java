package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

public class AttendanceResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

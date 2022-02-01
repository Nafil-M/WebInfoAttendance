package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

public class ProjectDetailsResponse {

    @SerializedName("status")
    public boolean status;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

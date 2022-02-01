package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllProjectsResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("data")
    public List<AllProjectsData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AllProjectsData> getData() {
        return data;
    }

    public void setData(List<AllProjectsData> data) {
        this.data = data;
    }
}

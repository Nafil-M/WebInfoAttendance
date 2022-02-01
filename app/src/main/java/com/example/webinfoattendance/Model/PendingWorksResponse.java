package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingWorksResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("data")
    public List<PendingWorksData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PendingWorksData> getData() {
        return data;
    }

    public void setData(List<PendingWorksData> data) {
        this.data = data;
    }
}

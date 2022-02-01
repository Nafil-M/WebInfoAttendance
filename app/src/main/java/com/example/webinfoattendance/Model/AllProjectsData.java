package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllProjectsData implements Serializable {

    @SerializedName("pid")
    public String pid;

    @SerializedName("pCode")
    public String pCode;

    @SerializedName("pName")
    public String pName;

    @SerializedName("pDetails")
    public String pDetails;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDetails() {
        return pDetails;
    }

    public void setpDetails(String pDetails) {
        this.pDetails = pDetails;
    }
}

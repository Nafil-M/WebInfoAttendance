package com.example.webinfoattendance.Model;

import com.google.gson.annotations.SerializedName;

public class PendingWorksData {

    @SerializedName("id")
    public String id;

    @SerializedName("project_id")
    public String project_id;

    @SerializedName("task")
    public String task;

    @SerializedName("status")
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

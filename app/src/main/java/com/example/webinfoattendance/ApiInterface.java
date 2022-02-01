package com.example.webinfoattendance;

import com.example.webinfoattendance.Model.AllProjectsResponse;
import com.example.webinfoattendance.Model.AttendanceResponse;
import com.example.webinfoattendance.Model.LoginResponse;
import com.example.webinfoattendance.Model.PendingWorksResponse;
import com.example.webinfoattendance.Model.ProjectDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api_login.php")
    Call<LoginResponse> login(@Field("Username") String Username,@Field("Password") String Password);

    @FormUrlEncoded
    @POST("api_attendance.php")
    Call<AttendanceResponse> attendance(@Field("EmployeeId") String EmployeeId, @Field("date_time") String date_time, @Field("latitude") Double latitude ,@Field("longitude") Double longitude, @Field("type") String type);

    @FormUrlEncoded
    @POST("api_list_projects.php")
    Call<AllProjectsResponse> allprojects(@Field("employee_id") String employee_id);

    @FormUrlEncoded
    @POST("api_pending_projects.php")
    Call<PendingWorksResponse> pendingworks(@Field("project_name") String project_name);

    @FormUrlEncoded
    @POST("api_pending_projectdetails.php")
    Call<ProjectDetailsResponse> projectdetails(@Field("task") String task, @Field("start_time") String start_time,
                                                @Field("end_time") String end_time, @Field("remarks") String remarks, @Field("status") String status);

}

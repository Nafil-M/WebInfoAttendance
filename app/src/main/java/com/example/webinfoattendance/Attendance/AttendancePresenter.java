package com.example.webinfoattendance.Attendance;

import android.util.Log;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Login.LoginPresenter;
import com.example.webinfoattendance.Model.AttendanceResponse;
import com.example.webinfoattendance.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendancePresenter {

    public void attendancee(final AttendancePresenter.AttendanceInterface AI, String user_id, String date_time, Double latitude, Double longiude, String type) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<AttendanceResponse> call = service.attendance(user_id, date_time, latitude, longiude,type);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                AttendanceResponse attendanceResponse = response.body();
                if (attendanceResponse != null) {
                    if (attendanceResponse.isStatus()) {
                        AI.onAttendanceSuccess(attendanceResponse);
                    } else {
                        AI.onAttendanceFailure("something occured");
                    }
                } else {
                    AI.onAttendanceFailure("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                AI.onAttendanceFailure("An error occurred");
                t.getMessage();
                Log.e("TAG", "onFailure: yoo " + t.getMessage());
            }
        });
    }

    public interface AttendanceInterface {
        void onAttendanceSuccess(AttendanceResponse attendanceResponse);

        void onAttendanceFailure(String msg);
    }

}

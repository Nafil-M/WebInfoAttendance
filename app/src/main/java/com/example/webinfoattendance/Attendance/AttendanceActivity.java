package com.example.webinfoattendance.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webinfoattendance.MainActivity;
import com.example.webinfoattendance.Model.AttendanceResponse;
import com.example.webinfoattendance.Model.LoginResponse;
import com.example.webinfoattendance.MyService;
import com.example.webinfoattendance.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity implements AttendancePresenter.AttendanceInterface{

    double lat,lng;
    String currentDate,currentTime,dateandtime;
    TextView punchIn,punchOut;
    private MyService myService;
    String userId;
    AttendancePresenter attendancePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);



        punchIn = findViewById(R.id.punchin);
        punchOut = findViewById(R.id.punchout);

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userid");

        attendancePresenter = new AttendancePresenter();

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
        dateandtime = currentDate + " " + currentTime;

        myService = new MyService(AttendanceActivity.this);


        punchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    lat = myService.getLatitude();
                    lng = myService.getLongitude();

                    punchInApiCall();
                    Toast.makeText(AttendanceActivity.this, "Punch In successful", Toast.LENGTH_LONG).show();

                    Log.e("TAG", "lat and lng =  " + lat + " , " + lng + "date and time are : " + dateandtime );

                    punchIn.setTextColor(getResources().getColor(R.color.disable1));
                    punchIn.setBackgroundResource(R.drawable.disabled);
                punchIn.setEnabled(false);
            }
        });

        punchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    lat = myService.getLatitude();
                    lng = myService.getLongitude();

                    punchOutApiCall();

                    Toast.makeText(AttendanceActivity.this, "Punch Out successful", Toast.LENGTH_LONG).show();

                    Log.e("TAG", "lat and lng =  " + lat + " , " + lng + "date and time are : " + dateandtime);

                    punchOut.setEnabled(false);
                back();

            }
        });


    }

    public void punchInApiCall(){

        attendancePresenter.attendancee(this,userId,dateandtime,lat,lng,"1");

    }

    public void punchOutApiCall(){

        attendancePresenter.attendancee(this,userId,dateandtime,lat,lng,"2");

    }

    public void back(){
        finish();
    }

    @Override
    public void onAttendanceSuccess(AttendanceResponse attendanceResponse) {

        Toast.makeText(this, "Status Updated", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAttendanceFailure(String msg) {

        Toast.makeText(this, "Attendance Api failed", Toast.LENGTH_SHORT).show();

    }
}
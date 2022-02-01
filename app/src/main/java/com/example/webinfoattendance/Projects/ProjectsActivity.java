package com.example.webinfoattendance.Projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Login.LoginActivity;
import com.example.webinfoattendance.MainActivity;
import com.example.webinfoattendance.Model.AllProjectsData;
import com.example.webinfoattendance.Model.AllProjectsResponse;
import com.example.webinfoattendance.Model.PendingWorksResponse;
import com.example.webinfoattendance.Model.ProjectDetailsResponse;
import com.example.webinfoattendance.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsActivity extends AppCompatActivity implements AllProjectsPresenter.AllProjectsInterface,PendingWorksPresenter.PendingWorksInterface,ProjectDetailsPresenter.ProjectDetailsInterface{

    Spinner spinnerproject,spinnerPendingproject;
    EditText startTime,endTime,remarks;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
    TextView savebtn;
    String radiostatus = "Pending";
    String start,end,remark;
    String userId;
    String name;
    String selectedItem,selectedpendingItem;
    AllProjectsPresenter allProjectsPresenter;
    PendingWorksPresenter pendingWorksPresenter;
    ProjectDetailsPresenter projectDetailsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        spinnerproject = findViewById(R.id.spinner1);
        spinnerPendingproject = findViewById(R.id.spinner2);

        radioButton1 = findViewById(R.id.radio0);
        radioButton2 = findViewById(R.id.radio1);
        radioGroup = findViewById(R.id.radioGroup1);
        startTime = findViewById(R.id.starttime);
        endTime = findViewById(R.id.endtime);
        remarks = findViewById(R.id.remarks);
        savebtn = findViewById(R.id.savebtn);
        allProjectsPresenter = new AllProjectsPresenter();
        pendingWorksPresenter = new PendingWorksPresenter();
        projectDetailsPresenter = new ProjectDetailsPresenter();

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userid");
        name = intent.getExtras().getString("uname");


                showAllProjects();

        spinnerproject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = parent.getSelectedItem().toString();
                showPendingProjects();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(ProjectsActivity.this, "Please select", Toast.LENGTH_SHORT).show();
            }
        });


        spinnerPendingproject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedpendingItem = parent.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(ProjectsActivity.this, "Please select", Toast.LENGTH_SHORT).show();
            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = startTime.getText().toString();
                end = endTime.getText().toString();
                remark = remarks.getText().toString();

                if (radioButton1.isChecked()){
                    radiostatus = "Completed";
                    Log.e("TAG", "radiostatus " + radiostatus );
                }else if(radioButton2.isChecked()){
                    radiostatus = "Pending";
                    Log.e("TAG", "radiostatus " + radiostatus );
                }else{
                    Toast.makeText(ProjectsActivity.this, "Please select status", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(start) || TextUtils.isEmpty(end) || TextUtils.isEmpty(remark)){
                    Toast.makeText(ProjectsActivity.this, "All fields are important", Toast.LENGTH_SHORT).show();
                }
                else{
                    projectDetailsApi();
//                    Toast.makeText(ProjectsActivity.this, "passing values are : " + start + " , " + end + " , " + remark + " , " + radiostatus, Toast.LENGTH_LONG).show();
                    Log.e("TAG", "passing values are : " + start + " , " + end + " , " + remark + " , " + radiostatus);
                }
            }
        });

    }

    public void showAllProjects(){
        allProjectsPresenter.allprojectsss(this,userId);
    }

    public void showPendingProjects(){
       pendingWorksPresenter.pendingworksss(this,selectedItem);
    }

    public void projectDetailsApi(){
        projectDetailsPresenter.projectdetailsss(this,selectedpendingItem,start,end,remark,radiostatus);
    }

    @Override
    public void onAllProjectSuccess(AllProjectsResponse allProjectsResponse) {


//        Toast.makeText(this, "all project api success", Toast.LENGTH_SHORT).show();

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < allProjectsResponse.getData().size(); i++) {
            arrayList.add(allProjectsResponse.getData().get(i).getpName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.single_drop_down, arrayList);
        adapter.setDropDownViewResource(R.layout.single_drop_down);
        spinnerproject.setAdapter(adapter);

    }

    @Override
    public void onAllProjectFailure(String msg) {
        Toast.makeText(this, "all project api fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPendingWorkSuccess(PendingWorksResponse pendingWorksResponse) {

//        Toast.makeText(this, "pending work api success", Toast.LENGTH_SHORT).show();

        ArrayList<String> arrayList2 = new ArrayList<String>();

            for (int i = 0; i < pendingWorksResponse.getData().size(); i++) {
                arrayList2.add(pendingWorksResponse.getData().get(i).getTask());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.single_drop_down, arrayList2);
            adapter.setDropDownViewResource(R.layout.single_drop_down);
            spinnerPendingproject.setAdapter(adapter);

    }

    @Override
    public void onPendingWorkFailure(String msg) {
        Toast.makeText(this, "No Pending Works found..Please select another project.", Toast.LENGTH_LONG).show();

        ArrayList<String> arrayList3 = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.single_drop_down, arrayList3);
        adapter.setDropDownViewResource(R.layout.single_drop_down);
        spinnerPendingproject.setAdapter(adapter);

        selectedpendingItem = "";
    }

    @Override
    public void onProjectDetailsSuccess(ProjectDetailsResponse projectDetailsResponse) {
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProjectsActivity.this, MainActivity.class);
        intent.putExtra("userid", userId);
        intent.putExtra("uname", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onProjectDetailsFailure(String msg) {
        Toast.makeText(this, "No pending Works Available..Please select another Project.", Toast.LENGTH_SHORT).show();
    }
}

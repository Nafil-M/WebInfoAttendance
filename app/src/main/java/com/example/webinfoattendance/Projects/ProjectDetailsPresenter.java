package com.example.webinfoattendance.Projects;

import android.util.Log;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Model.AllProjectsResponse;
import com.example.webinfoattendance.Model.ProjectDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailsPresenter {

    public void projectdetailsss(final ProjectDetailsPresenter.ProjectDetailsInterface PDI,String task, String starttime, String endtime, String remarks, String status) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ProjectDetailsResponse> call = service.projectdetails(task,starttime,endtime,remarks,status);
        call.enqueue(new Callback<ProjectDetailsResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailsResponse> call, Response<ProjectDetailsResponse> response) {
                ProjectDetailsResponse projectDetailsResponse = response.body();
                if (projectDetailsResponse != null) {
                    if (projectDetailsResponse.isStatus()) {
                        PDI.onProjectDetailsSuccess(projectDetailsResponse);
                    } else {
                        PDI.onProjectDetailsFailure("something occured");
                    }
                } else {
                    PDI.onProjectDetailsFailure("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailsResponse> call, Throwable t) {
                PDI.onProjectDetailsFailure("An error occurred");
                t.getMessage();
                Log.e("TAG", "onFailure: yoo " + t.getMessage());
            }
        });
    }

    public interface ProjectDetailsInterface {
        void onProjectDetailsSuccess(ProjectDetailsResponse projectDetailsResponse);

        void onProjectDetailsFailure(String msg);
    }

}

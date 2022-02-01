package com.example.webinfoattendance.Projects;

import android.util.Log;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Login.LoginPresenter;
import com.example.webinfoattendance.Model.AllProjectsResponse;
import com.example.webinfoattendance.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProjectsPresenter {


    public void allprojectsss(final AllProjectsPresenter.AllProjectsInterface ALI,String employeeID) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<AllProjectsResponse> call = service.allprojects(employeeID);
        call.enqueue(new Callback<AllProjectsResponse>() {
            @Override
            public void onResponse(Call<AllProjectsResponse> call, Response<AllProjectsResponse> response) {
                AllProjectsResponse allProjectsResponse = response.body();
                if (allProjectsResponse != null) {
                    if (allProjectsResponse.isStatus()) {
                        ALI.onAllProjectSuccess(allProjectsResponse);
                    } else {
                        ALI.onAllProjectFailure("something occured");
                    }
                } else {
                    ALI.onAllProjectFailure("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<AllProjectsResponse> call, Throwable t) {
                ALI.onAllProjectFailure("An error occurred");
                t.getMessage();
                Log.e("TAG", "onFailure: yoo " + t.getMessage());
            }
        });
    }

    public interface AllProjectsInterface {
        void onAllProjectSuccess(AllProjectsResponse allProjectsResponse);

        void onAllProjectFailure(String msg);
    }


}

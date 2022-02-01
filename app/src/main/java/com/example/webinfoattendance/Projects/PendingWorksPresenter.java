package com.example.webinfoattendance.Projects;

import android.util.Log;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Model.AllProjectsResponse;
import com.example.webinfoattendance.Model.PendingWorksResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingWorksPresenter {

    public void pendingworksss(final PendingWorksPresenter.PendingWorksInterface PI,String projectname) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<PendingWorksResponse> call = service.pendingworks(projectname);
        call.enqueue(new Callback<PendingWorksResponse>() {
            @Override
            public void onResponse(Call<PendingWorksResponse> call, Response<PendingWorksResponse> response) {
                PendingWorksResponse pendingWorksResponse = response.body();
                if (pendingWorksResponse != null) {
                    if (pendingWorksResponse.isStatus()) {
                        PI.onPendingWorkSuccess(pendingWorksResponse);
                    } else {
                        PI.onPendingWorkFailure("something occured");
                    }
                } else {
                    PI.onPendingWorkFailure("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<PendingWorksResponse> call, Throwable t) {
                PI.onPendingWorkFailure("An error occurred");
                t.getMessage();
                Log.e("TAG", "onFailure: yoo " + t.getMessage());
            }
        });
    }

    public interface PendingWorksInterface {
        void onPendingWorkSuccess(PendingWorksResponse pendingWorksResponse);

        void onPendingWorkFailure(String msg);
    }


}

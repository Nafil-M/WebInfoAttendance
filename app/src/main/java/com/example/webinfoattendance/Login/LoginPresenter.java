package com.example.webinfoattendance.Login;

import android.util.Log;

import com.example.webinfoattendance.ApiClient;
import com.example.webinfoattendance.ApiInterface;
import com.example.webinfoattendance.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    public void loginn(final LoginPresenter.LoginInterface LI, String Username, String Password) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = service.login(Username, Password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    if (loginResponse.isStatus()) {
                        LI.onLoginSuccess(loginResponse);
                    } else {
                        LI.onLoginFailure("something occured");
                    }
                } else {
                    LI.onLoginFailure("Something wrong");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LI.onLoginFailure("An error occurred");
                t.getMessage();
                Log.e("TAG", "onFailure: yoo " + t.getMessage());
            }
        });
    }

    public interface LoginInterface {
        void onLoginSuccess(LoginResponse loginResponse);

        void onLoginFailure(String msg);
    }
}



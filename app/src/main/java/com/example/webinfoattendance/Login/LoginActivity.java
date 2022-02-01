package com.example.webinfoattendance.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webinfoattendance.MainActivity;
import com.example.webinfoattendance.Model.LoginResponse;
import com.example.webinfoattendance.MyService;
import com.example.webinfoattendance.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginInterface{

    EditText userName,passWord;
    String name,pass;
    TextView login,rememberMe;
    ProgressDialog progressDialog;
    private MyService myService;
    String userId;
    LoginPresenter loginPresenter;
    LoginResponse loginResponse;
    ImageView showpassword;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.edittext);
        passWord = findViewById(R.id.edittext2);
        login = findViewById(R.id.loginbtn);
        showpassword = findViewById(R.id.imagepassword);
        rememberMe = findViewById(R.id.remember);
        loginPresenter = new LoginPresenter();
        progressDialog = new ProgressDialog(this);
        myService = new MyService(LoginActivity.this);
        loginResponse = new LoginResponse();


        if (!myService.canGetLocation()) {
            myService.showSettingsAlert();
        }

        showpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        passWord.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

//        if (!isConnected(this)){
//            androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//
//            // Setting Dialog Title
//            alertDialog.setTitle("NO CONNECTION!!");
//
//            // Setting Dialog Message
//            alertDialog.setMessage("Please turn on the Internet");
//
//            // On pressing Settings button
//            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog,int which) {
//
//                }
//            });

//            // on pressing cancel button
//            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            alertDialog.show();
//        }

        rememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = userName.getText().toString();
                pass = passWord.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "All Fields are Important", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Username and Password saved", Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.putString("Unm", name);
                    Ed.putString("Psw", pass);
                    Ed.commit();
                }
            }
        });

        name = userName.getText().toString();
        pass = passWord.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
            SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);

            String unm = sp1.getString("Unm", null);
            String pss = sp1.getString("Psw", null);

            userName.setText(unm);
            passWord.setText(pss);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = userName.getText().toString();
                pass = passWord.getText().toString();



                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "All Fields are Important", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setMessage("Please wait while Login..");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

//                    movetonextActivity();
                    Login();

                }
            }
        });


    }

    private void Login() {
        Log.e("TAG", "uname and pass " + name + "," + pass);
        loginPresenter.loginn(this,name,pass);
    }

//    private void movetonextActivity(){
//
//
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.putExtra("uname", name);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {

        userId = loginResponse.getUserid();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userid", userId);
        intent.putExtra("uname", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(this, "Login Success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(String msg) {

        Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
//    public boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager)context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()) {
//            try {
//                URL url = new URL("http://www.google.com/");
//                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
//                urlc.setRequestProperty("User-Agent", "test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1000); // mTimeout is in seconds
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (IOException e) {
//                Log.i("warning", "Error checking internet connection", e);
//                return false;
//            }
//        }
//
//        return false;
//
//    }

}
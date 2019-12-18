package com.qci.fish.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qci.fish.MainActivity;
import com.qci.fish.R;
import com.qci.fish.api.APIService;
import com.qci.fish.api.ApiUtils;
import com.qci.fish.pojo.LoginRequest;
import com.qci.fish.pojo.LoginResponse;
import com.qci.fish.util.AppConstants;
import com.qci.fish.util.AppDialog;
import com.qci.fish.util.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.signIn)
    Button signIn;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        mAPIService = ApiUtils.getAPIService();

        email.setText("admin@gmail.com");
        password.setText("Password1");
    }


    @OnClick(R.id.signIn)
    public void onViewClicked(){

        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent){
            if (email.getText().toString().length() == 0){
                Toast.makeText(LoginActivity.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
            } else if (password.getText().toString().length() == 0){
                Toast.makeText(LoginActivity.this,"Please enter the password",Toast.LENGTH_LONG).show();
            } else {
             LoginApi();
            }

        } else {
            Toast.makeText(LoginActivity.this, AppConstants.NO_INTERNET_CONNECTED,Toast.LENGTH_LONG).show();
        }
    }

    private void LoginApi(){
        LoginRequest request = new LoginRequest();
        request.setUserName(email.getText().toString());
        request.setPassword(password.getText().toString());
        request.setGrant_type("password");
        request.setRefreshToken("");


        final ProgressDialog d = AppDialog.showLoading(LoginActivity.this);
        d.setCanceledOnTouchOutside(false);

        mAPIService.loginrequest("application/json",request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                d.dismiss();

                if (response.body() != null){
                    if (response.body().getMessage() != null){
                        Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (response.body().getAccessToken() != null){
                        saveIntoPrefs(AppConstants.ACCESS_Token,response.body().getAccessToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"login failed",Toast.LENGTH_LONG).show();

                d.dismiss();
            }
        });
    }
}

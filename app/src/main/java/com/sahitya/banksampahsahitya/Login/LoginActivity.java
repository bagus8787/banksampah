package com.sahitya.banksampahsahitya.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.coordinator.HomeCoordinatorActivity;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.UserResponse;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.utils.TextValidator;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_lg)
    EditText email_lg;

    @BindView(R.id.password_lg)
    EditText password_lg;

    Context mContext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        savedInstanceState.clear();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mContext = this;

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        email_lg.addTextChangedListener(new TextValidator(email_lg) {
            @Override public void validate(TextView textView, String text) {
                if (text.length() == 0) {
                    textView.setError("Email Harus diisi");
                }
            }
        });

//        password_lg.addTextChangedListener(new TextValidator(password_lg) {
//            @Override public void validate(TextView textView, String text) {
//                if (text.length() == 0) {
//                    textView.setError("Password Harus diisi");
//                }
//            }
//        });


    }

    @OnClick(R.id.sa_register) void sa_register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(R.id.btn_login) void login() {
        if (email_lg.getText().toString().isEmpty()) {
            email_lg.setError("Email Harus diisi");
            email_lg.requestFocus();
            return;
        }
        if (password_lg.getText().toString().isEmpty()) {
            password_lg.setError("Password Harus diisi");
            password_lg.requestFocus();
            return;
        }
        progressDialog.show();
        Call<UserResponse> postLogin = apiInterface.postLogin(
                email_lg.getText().toString(),
                password_lg.getText().toString());
        postLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.code() >= 200 && response.code() < 300) {
                    User user = response.body().getUser();
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, user.getName());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, user.getEmail());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_MOBILE, user.getMobile());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_AVATAR, user.getAvatarLocation());

                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "Bearer " +response.body().getToken());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, user.getRoleName());

                    if (user.isUser()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else if (user.isCoordinator()) {
                        startActivity(new Intent(LoginActivity.this, HomeCoordinatorActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else if (user.isAdmin()) {
                        startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                } else {
                    try {
                        Gson gson = new Gson();
                        BaseResponse errorResponse = gson.fromJson(
                                response.errorBody().string(),
                                BaseResponse.class);

                        Toast.makeText(mContext, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){

                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
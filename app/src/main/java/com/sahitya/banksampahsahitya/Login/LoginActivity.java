package com.sahitya.banksampahsahitya.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.UserResponse;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

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

        mContext = this;

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

//        if (sharedPrefManager.getSPSudahLogin()){
//            if (sharedPrefManager.getRole().equals(Role.ROLE_USER)) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
//            } else if (sharedPrefManager.getRole().equals(Role.ROLE_COODINATOR)) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
//            } else if (sharedPrefManager.getRole().equals(Role.ROLE_ADMIN)) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
//            }
//        }

    }

    @OnClick(R.id.sa_register) void sa_register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(R.id.btn_login) void login() {
        progressDialog.show();
        Call<UserResponse> postLogin = apiInterface.postLogin(email_lg.getText().toString(),
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

                    sharedPrefManager.saveSPString(SharedPrefManager.SP_TOKEN, "Bearer " +response.body().getToken());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, user.getRoleName());

                    if (user.isUser()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
//                        Toast.makeText(mContext, "Warga", Toast.LENGTH_SHORT).show();
                    } else if (user.isCoordinator()) {
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        finish();
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        Toast.makeText(mContext, "Koor", Toast.LENGTH_SHORT).show();
                    } else if (user.isAdmin()) {
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        finish();
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        Toast.makeText(mContext, "Admin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Emai/Password salah", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
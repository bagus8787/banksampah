package com.sahitya.banksampahsahitya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sahitya.banksampahsahitya.Login.LoginActivity;
import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.coordinator.HomeCoordinatorActivity;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

public class ScreenActivity extends AppCompatActivity {
    Context mContext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        mContext = this;

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!sharedPrefManager.getSPSudahLogin()) {
                    Intent login = new Intent(ScreenActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                } else {
                    if (sharedPrefManager.getRole().equals(Role.ROLE_USER)) {
                        startActivity(new Intent(ScreenActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else if (sharedPrefManager.getRole().equals(Role.ROLE_COODINATOR)) {
                        startActivity(new Intent(ScreenActivity.this, HomeCoordinatorActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else if (sharedPrefManager.getRole().equals(Role.ROLE_ADMIN)) {
                        startActivity(new Intent(ScreenActivity.this, HomeAdminActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }
            }
        }, 2000);
    }
}
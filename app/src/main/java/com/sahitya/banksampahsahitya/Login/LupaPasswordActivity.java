package com.sahitya.banksampahsahitya.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sahitya.banksampahsahitya.R;

public class LupaPasswordActivity extends AppCompatActivity {

    Button btn_konfirm_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lupa Password");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_konfirm_pass = findViewById(R.id.btn_konfirm_pass);

        btn_konfirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LupaPasswordActivity.this, ResetPasswordActivity.class));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
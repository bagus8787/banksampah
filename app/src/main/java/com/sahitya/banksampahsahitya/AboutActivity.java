package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.coordinator.HomeCoordinatorActivity;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.user.EditProfilActivity;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

public class AboutActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());

        TextView show_term_condition = findViewById(R.id.show_term_condition);

        show_term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = new WebView(AboutActivity.this);
                String helpText = AboutActivity.this.getString(R.string.term_condition);
                webView.loadData(helpText, "text/html", "utf-8");
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setTitle(R.string.term)
                        .setView(webView)
                        .setNeutralButton("OK", null)
                        .show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
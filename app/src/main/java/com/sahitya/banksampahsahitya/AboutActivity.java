package com.sahitya.banksampahsahitya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
}
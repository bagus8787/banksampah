package com.sahitya.banksampahsahitya.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;

public class DetailUserActivity extends AppCompatActivity {

    TextView it_nama, it_no_telp, it_email;

    User user;

    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        user = getIntent().getParcelableExtra("DATA_USER");

        id_user = getIntent().getStringExtra("IT_ID_USER");

        it_nama = findViewById(R.id.it_nama);
        it_email = findViewById(R.id.it_email);
        it_no_telp = findViewById(R.id.no_tlp);

        it_nama.setText(user.getId());
    }
}
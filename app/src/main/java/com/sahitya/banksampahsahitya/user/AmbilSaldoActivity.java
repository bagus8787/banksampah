package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.repositories.ProfileRepository;

public class AmbilSaldoActivity extends AppCompatActivity {

    ProfileRepository profileRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_saldo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);


        profileRepository = new ProfileRepository();

        EditText jumlah = findViewById(R.id.ambil_jumlah);
        Button ambil_btn = findViewById(R.id.ambil_btn);

        ambil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileRepository.ambilPoint(Integer.valueOf(jumlah.getText().toString()));
                Toast.makeText(AmbilSaldoActivity.this, "Permintaan Ambil Saldo Berhasil!", Toast.LENGTH_SHORT).show();
                Toast.makeText(AmbilSaldoActivity.this, "Silahkan Hubungi Admin untuk Pengambilan!", Toast.LENGTH_SHORT).show();
                finish();
//                Toast.makeText(AmbilSaldoActivity.this, "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

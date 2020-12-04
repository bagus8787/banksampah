package com.sahitya.banksampahsahitya.base.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;

public class DetailBarangActivity extends AppCompatActivity {
    private BarangViewModel viewModel;
    private Integer IT_ID;
    String IT_NAME;
    Integer IT_POINT;
    String IT_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        IT_ID = getIntent().getIntExtra("IT_ID", 0);
        IT_NAME = getIntent().getStringExtra("IT_NAME");
        IT_POINT = getIntent().getIntExtra("IT_POINT", 0);
        IT_TYPE = getIntent().getStringExtra("IT_TYPE");

        TextView detail_nama = findViewById(R.id.detail_barang_nama);
        TextView detail_point = findViewById(R.id.detail_barang_point);
        TextView detail_type = findViewById(R.id.detail_barang_type);
        Button edit_barang = findViewById(R.id.detail_barang_edit_btn);

        detail_nama.setText(IT_NAME);
        detail_point.setText(IT_POINT.toString());
        detail_type.setText(IT_TYPE);

        edit_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailBarangActivity.this, FormBarangActivity.class)
                        .putExtra("IT_ID", IT_ID)
                        .putExtra("IT_NAME", IT_NAME)
                        .putExtra("IT_POINT", IT_POINT)
                        .putExtra("IT_TYPE", IT_TYPE)
                        .putExtra("ACTION", "EDIT"));
            }
        });

        viewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
        viewModel.init();
        viewModel.getBarangResponseLiveData().observe(this, new Observer<Barang>() {
            @Override
            public void onChanged(Barang barangResponse) {
                if (barangResponse != null) {
                    IT_NAME = barangResponse.getName();
                    IT_POINT = barangResponse.getPoint();
                    IT_TYPE = barangResponse.getType();
                    detail_nama.setText(IT_NAME);
                    detail_point.setText(IT_POINT.toString());
                    detail_type.setText(IT_TYPE);
                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        viewModel.getBarang(IT_ID);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

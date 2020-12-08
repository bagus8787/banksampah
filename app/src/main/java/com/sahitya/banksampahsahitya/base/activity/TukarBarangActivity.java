package com.sahitya.banksampahsahitya.base.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.repositories.KasirRepository;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;
import com.sahitya.banksampahsahitya.viewmodels.KasirViewModel;

import java.util.ArrayList;

public class TukarBarangActivity extends AppCompatActivity {


    BarangViewModel barangViewModel;
    KasirViewModel kasirViewModel;
    KasirRepository kasirRepository;
    ArrayAdapter<Barang> adapter;

    String IT_BARCODE = "";
    Integer WARGA_ID;
    Warga warga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_barang);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        IT_BARCODE = getIntent().getStringExtra("IT_BARCODE");

        kasirRepository = new KasirRepository();

        TextView tukar_nama = findViewById(R.id.form_tukar_nama);
        Spinner tukar_item = findViewById(R.id.form_tukar_item);
        EditText tukar_count = findViewById(R.id.form_tukar_count);
        Button btn_tukar = findViewById(R.id.tukar_barang_btn);

        btn_tukar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer barang_id = adapter.getItem(((int) tukar_item.getSelectedItemId())).getId();
                Log.d("tukar", barang_id.toString());
                kasirRepository.tukarBarang(WARGA_ID, barang_id, Float.valueOf(tukar_count.getText().toString()));
                finish();
            }
        });

        barangViewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
        barangViewModel.init();
        barangViewModel.getBarangsResponseLiveData().observe(this, new Observer<ArrayList<Barang>>() {
            @Override
            public void onChanged(ArrayList<Barang> barangsResponse) {
                if (barangsResponse != null) {
                    adapter = new ArrayAdapter<Barang>(TukarBarangActivity.this, android.R.layout.simple_spinner_dropdown_item, barangsResponse);
                    adapter.setDropDownViewResource(R.layout.spinner_barang_list);
                    tukar_item.setAdapter(adapter);
                }
            }
        });

        kasirViewModel = ViewModelProviders.of(this).get(KasirViewModel.class);
        kasirViewModel.init();
        kasirViewModel.getWargaResponseLiveData().observe(this, new Observer<Warga>() {
            @Override
            public void onChanged(Warga barangsResponse) {
                if (barangsResponse != null) {
                    warga = barangsResponse;
                    tukar_nama.setText(warga.getName());
                    WARGA_ID = warga.getId();
                }
            }
        });

        barangViewModel.getBarangs("");
        kasirViewModel.getWargaByBarcode(IT_BARCODE);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}

package com.sahitya.banksampahsahitya.base.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;

import java.util.ArrayList;

public class FormBarangActivity extends AppCompatActivity {
    private BarangViewModel viewModel;
    private Integer IT_ID, IT_POINT;
    private String IT_NAME, IT_TYPE, ACTION;

    private EditText form_nama, form_point;
    private TextView form_title;
    private Button form_save;
    private Spinner form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_barang);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.

        IT_ID = getIntent().getIntExtra("IT_ID", 0);
        IT_NAME = getIntent().getStringExtra("IT_NAME");
        IT_POINT = getIntent().getIntExtra("IT_POINT", 0);
        IT_TYPE = getIntent().getStringExtra("IT_TYPE");
        ACTION = getIntent().getStringExtra("ACTION");

        form_title = findViewById(R.id.form_barang_title);
        form_nama = findViewById(R.id.form_barang_nama);
        form_type = findViewById(R.id.form_barang_type);
        form_point = findViewById(R.id.form_barang_point);
        form_save = findViewById(R.id.form_barang_save_btn);

        if (ACTION.equals("EDIT")) {
            form_title.setText("Edit Barang");
            form_nama.setText(IT_NAME);
            form_type.setSelection(getIndex(form_type, IT_TYPE));
            form_point.setText(IT_POINT.toString());
        } else if (ACTION.equals("CREATE")) {
            form_title.setText("Buat Barang Baru");
        }

        form_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = form_nama.getText().toString();
                String type = form_type.getSelectedItem().toString();
                Integer point = Integer.valueOf(form_point.getText().toString());
                
                if (nama.isEmpty() || type.isEmpty() || point == 0) {
                    if (nama.isEmpty()) {
                        Toast.makeText(FormBarangActivity.this, "Nama harus diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (type.isEmpty()) {
                        Toast.makeText(FormBarangActivity.this, "Tipe harus diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (point == 0 || point == null) {
                        Toast.makeText(FormBarangActivity.this, "Point harus diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    saveBarang(nama, point, type);
                    Toast.makeText(FormBarangActivity.this, "Barang Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        viewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
        viewModel.init();

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return -1;
    }

    private void saveBarang(String nama, Integer point, String type) {
        if (ACTION.equals("EDIT")) {
            viewModel.updateBarang(IT_ID, nama, point, type);
        } else if (ACTION.equals("CREATE")) {
            viewModel.storeBarang(nama, point, type);
        }
    }
}

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
import com.sahitya.banksampahsahitya.utils.TextValidator;
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

        form_point.addTextChangedListener(new TextValidator(form_point) {
            @Override public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    textView.setError("Harga Harus diisi");
                    textView.setText("0");
                }
            }
        });

        form_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = form_nama.getText().toString();
                String type = form_type.getSelectedItem().toString();
                Integer point = Integer.parseInt(form_point.getText().toString());

                if (nama.isEmpty()) {
                    form_nama.setError("Nama harus diisi");
                    form_nama.requestFocus();
                    return;
                }
                if (type.isEmpty()) {
                    Toast.makeText(FormBarangActivity.this, "Pilih jenis sampah!", Toast.LENGTH_SHORT).show();
                    form_type.requestFocus();
                    return;
                }
                if (form_point.getText().toString().isEmpty()) {
                    form_point.setError("Harga harus diisi");
                    form_point.requestFocus();
                    return;
                }

                saveBarang(nama, point, type);
                Toast.makeText(FormBarangActivity.this, "Barang Disimpan", Toast.LENGTH_SHORT).show();
                finish();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

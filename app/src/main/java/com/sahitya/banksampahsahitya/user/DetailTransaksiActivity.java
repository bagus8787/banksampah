package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailTransaksiActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        String IT_VERIFIED = getIntent().getStringExtra("IT_VERIFIED");
        Integer IT_POINT = getIntent().getIntExtra("IT_POINT", 0);
        String IT_BARCODE = getIntent().getStringExtra("IT_BARCODE");
        String IT_TYPE = getIntent().getStringExtra("IT_TYPE");
        String IT_DESCRIPTION = getIntent().getStringExtra("IT_DESCRIPTION");
        Integer IT_POINT_TOTAL = getIntent().getIntExtra("IT_POINT_TOTAL", 0);

//        date
        String IT_CREATED = getIntent().getStringExtra("IT_CREATED");
        String IT_UPDATED = getIntent().getStringExtra("IT_UPDATED");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");


        TextView ambil_verified = findViewById(R.id.detail_ambil_verified);
        TextView ambil_point = findViewById(R.id.detail_ambil_point);
        ImageView ambil_barcode = findViewById(R.id.detail_ambil_barcode);
        TextView ambil_description = findViewById(R.id.detail_ambil_description);

        TextView ambil_created = findViewById(R.id.detail_created);
        TextView ambil_updated = findViewById(R.id.detail_update);

        //text di bawah scan
        TextView txt_scan = findViewById(R.id.txt_scan);

        //image checklist verifikasi
        ImageView img_check = findViewById(R.id.img_check);

        if (IT_BARCODE != "" && IT_BARCODE != null) {
            BarcodeImage barcode = new BarcodeImage(IT_BARCODE);
            ambil_barcode.setVisibility(View.VISIBLE);
            ambil_barcode.setImageBitmap(barcode.getImage());

        }

        if (IT_TYPE.equals("tukar")) {
            ambil_point.setText("+ Rp. " + IT_POINT_TOTAL.toString());
            if (IT_VERIFIED.equals("Verified")){
                img_check.setVisibility(View.VISIBLE);
                ambil_updated.setText(IT_UPDATED);

            } else {
                ambil_updated.setText("-");
//                txt_scan.setVisibility(View.VISIBLE);
            }
        } else {
            ambil_point.setText("- Rp. " + IT_POINT.toString());

            if (IT_VERIFIED.equals("Verified")){
                img_check.setVisibility(View.VISIBLE);
                ambil_updated.setText(IT_UPDATED);

            } else if (sharedPrefManager.isUser()) {
                txt_scan.setVisibility(View.VISIBLE);
                ambil_updated.setText("-");

            }

        }

        ambil_verified.setText(IT_VERIFIED);
        ambil_description.setText(IT_DESCRIPTION);

//        date
        ambil_created.setText(IT_CREATED);
//        ambil_updated.setText(IT_UPDATED);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

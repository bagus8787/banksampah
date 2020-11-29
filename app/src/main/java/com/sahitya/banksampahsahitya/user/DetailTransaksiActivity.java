package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.BarcodeImage;

public class DetailTransaksiActivity extends AppCompatActivity {

    String IT_VERIFIED;
    Integer IT_POINT = 0;
    String IT_BARCODE = "";
    BarcodeImage barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        IT_VERIFIED = getIntent().getStringExtra("IT_VERIFIED");
        IT_POINT = getIntent().getIntExtra("IT_POINT", 0);
        IT_BARCODE = getIntent().getStringExtra("IT_BARCODE");

        barcode = new BarcodeImage(IT_BARCODE);

        TextView ambil_verified = findViewById(R.id.detail_ambil_verified);
        TextView ambil_point = findViewById(R.id.detail_ambil_point);
        ImageView ambil_barcode = findViewById(R.id.detail_ambil_barcode);
        ambil_verified.setText(IT_VERIFIED);
        ambil_point.setText(IT_POINT.toString());
        ambil_barcode.setImageBitmap(barcode.getImage());
    }
}

package com.sahitya.banksampahsahitya.coordinator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.viewmodels.PointHistoryViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class DetailTransaksiActivity extends AppCompatActivity {

    String IT_BARCODE = "";

    private PointHistoryViewModel viewModel;
    private  PointHistory pointHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_transaksi);

        IT_BARCODE = getIntent().getStringExtra("IT_BARCODE");


        TextView ambil_verified = findViewById(R.id.detail_ambil_verified);
        TextView ambil_point = findViewById(R.id.detail_ambil_point);
        TextView ambil_nama = findViewById(R.id.detail_ambil_nama);
        TextView ambil_email = findViewById(R.id.detail_ambil_email);
        Button btn_verify = findViewById(R.id.detail_ambil_verify_btn);


        viewModel = ViewModelProviders.of(this).get(PointHistoryViewModel.class);
        viewModel.init();
        viewModel.getPointResponseLiveData().observe(this, new Observer<PointHistory>() {
            @Override
            public void onChanged(PointHistory pointResponse) {
                if (pointResponse != null) {
                    ambil_verified.setText(pointResponse.isVerified() ? "Verified" : "Unverified");
                    ambil_point.setText(Integer.toString(pointResponse.getPoint()));
                    ambil_nama.setText(pointResponse.getWarga().getName());
                    ambil_email.setText(pointResponse.getWarga().getEmail().concat(" : ").concat(pointResponse.getWarga().getMobile()));
                }
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.transaksiVerify(IT_BARCODE);
                finish();
            }
        });

        viewModel.getTransaksi(IT_BARCODE);

    }
}

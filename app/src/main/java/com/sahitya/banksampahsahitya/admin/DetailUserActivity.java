package com.sahitya.banksampahsahitya.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    EditText it_nama, it_no_telp, it_email, it_sex;
    Button btn_coor;

    User user;

    Warga warga;

    int roles = 0;

    String id_user, nama_user;

    Context mContext;

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        mContext = this;

        sharedPrefManager = new SharedPrefManager(this);

        user = getIntent().getParcelableExtra("DATA_USER");

        id_user = getIntent().getStringExtra("IT_ID_USER");
        nama_user = getIntent().getStringExtra("IT_NAMA_USER");

        it_nama = findViewById(R.id.it_nama);
        it_email = findViewById(R.id.it_email);
        it_no_telp = findViewById(R.id.it_no_tlp);
        it_sex = findViewById(R.id.it_jenkel);

        it_nama.setText(nama_user);
        it_sex.setText(id_user);

        btn_coor = findViewById(R.id.btn_coor);

        Log.d("shared : ", String.valueOf(nama_user));

        btn_coor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Warga> AdminUpdateUser = apiInterface.UpdateUser(
                        sharedPrefManager.getSPToken(),
                        Integer.valueOf(id_user),
                        it_nama.getText().toString(),
                        it_email.getText().toString(),
                        it_sex.getText().toString()
                        );
                AdminUpdateUser.enqueue(new Callback<Warga>() {
                @Override
                public void onResponse(Call<Warga> call, Response<Warga> response) {
                    progressDialog.dismiss();

                    Log.d("isinyaa : ", String.valueOf(AdminUpdateUser));

                    if (response.code() >= 200 && response.code() < 300) {
//                        String message = response.body().getMessage();
                        Log.d("pesannya", String.valueOf(response.code()));
//                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(mContext, "Ada kesalahan", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Warga> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
            }
        });
    }
}
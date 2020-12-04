package com.sahitya.banksampahsahitya.admin;

import androidx.appcompat.app.ActionBar;
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
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    EditText it_nama, it_no_telp, it_email, it_sex, it_address;
    Button btn_coor, btn_update;

    User user;

    Warga warga;

    int roles = 0;

    Integer id_user;
    String nama_user, email_user, sex_user, nope_user, address_user;

    Context mContext;

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mContext = this;

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        id_user = getIntent().getIntExtra("IT_ID_USER", 0);
        Log.d("User", id_user.toString());
        nama_user = getIntent().getStringExtra("IT_NAMA_USER");
        sex_user = getIntent().getStringExtra("IT_SEX_USER");
        email_user = getIntent().getStringExtra("IT_EMAIL_USER");
        nope_user = getIntent().getStringExtra("IT_NOPE_USER");
        address_user = getIntent().getStringExtra("IT_ADDRESS_USER");

        it_nama = findViewById(R.id.it_nama);
        it_email = findViewById(R.id.it_email);
        it_no_telp = findViewById(R.id.it_no_tlp);
        it_sex = findViewById(R.id.it_jenkel);
        it_address = findViewById(R.id.it_alamat);

        it_nama.setText(nama_user);
        it_sex.setText(sex_user);
        it_address.setText(address_user);
        it_email.setText(email_user);
        it_no_telp.setText(nope_user);

        btn_update = findViewById(R.id.btn_edit_profile);
        btn_coor = findViewById(R.id.btn_coor);

        Log.d("shared : ", String.valueOf(nama_user));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Warga> updateWarga = apiInterface.updateWarga(
                        sharedPrefManager.getSPToken(),
                        id_user,
                        it_nama.getText().toString(),
                        it_address.getText().toString(),
                        it_sex.getText().toString()
                        );
                updateWarga.enqueue(new Callback<Warga>() {
                @Override
                public void onResponse(Call<Warga> call, Response<Warga> response) {
                    if (response.code() >= 200 && response.code() < 300) {
//                        String message = response.body().getMessage();
                        Log.d("pesannya", String.valueOf(response.code()));
                        Toast.makeText(mContext, "User berhasil di update", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "Ada kesalahan", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Warga> call, Throwable t) {

                }
            });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
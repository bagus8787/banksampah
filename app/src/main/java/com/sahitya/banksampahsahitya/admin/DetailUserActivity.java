package com.sahitya.banksampahsahitya.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.user.Fragment.HistoryFragment;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    EditText it_nama, it_no_telp, it_email, it_address, password, pass_confirm;
    Button btn_update;
    Spinner it_sex, rt;

    User user;

    Warga warga;

    int roles = 0;

    Integer id_user;
    String nama_user, email_user, sex_user, nope_user, address_user, rtuser, avatar;
    ImageView it_avatar;

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
        rtuser = getIntent().getStringExtra("IT_RT_USER");
        avatar = getIntent().getStringExtra("IT_AVATAR_USER");
        Boolean hasWarga = getIntent().getBooleanExtra("IT_HAS_WARGA", false);

        it_nama = findViewById(R.id.it_nama);
        it_email = findViewById(R.id.it_email);
        it_no_telp = findViewById(R.id.it_no_tlp);
        it_sex = findViewById(R.id.it_jenkel);
        it_address = findViewById(R.id.it_alamat);
        password = findViewById(R.id.it_password_p2);
        pass_confirm = findViewById(R.id.it_password_confirm_p2);
        rt = findViewById(R.id.it_rt_p2);
        it_avatar = findViewById(R.id.it_avatar);

        Picasso.get().load(avatar)
                .fit()
                .error(R.drawable.ic_baseline_account_circle)
                .into(it_avatar);

        it_nama.setHint(nama_user);
        it_email.setHint(email_user);
        it_no_telp.setHint(nope_user);
        if (hasWarga) {
            rt.setSelection(getIndex(rt, rtuser));
            it_sex.setSelection(getIndex(it_sex, sex_user));
            it_address.setHint(address_user);
        } else {
            rt.setVisibility(View.GONE);
            it_sex.setVisibility(View.GONE);
            it_address.setVisibility(View.GONE);
            findViewById(R.id.lbl_it_gender).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_rt).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_address).setVisibility(View.GONE);
        }

        btn_update = findViewById(R.id.btn_edit_profile);

        Log.d("shared : ", String.valueOf(address_user));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Warga> updateWarga = apiInterface.updateWarga(
                        sharedPrefManager.getSPToken(),
                        id_user,
                        it_nama.getText().toString(),
                        it_no_telp.getText().toString(),
                        it_address.getText().toString(),
                        it_sex.getSelectedItem().toString(),
                        it_email.getText().toString(),
                        rt.getSelectedItem().toString(),
                        password.getText().toString(),
                        pass_confirm.getText().toString()
                        );
                updateWarga.enqueue(new Callback<Warga>() {
                @Override
                public void onResponse(Call<Warga> call, Response<Warga> response) {
                    if (response.code() >= 200 && response.code() < 300) {
//                        String message = response.body().getMessage();
                        Log.d("pesannya", String.valueOf(response.code()));
                        Toast.makeText(mContext, "User berhasil di update", Toast.LENGTH_SHORT).show();

                    } else {
                        try {
                            Gson gson = new Gson();
                            BaseResponse errorResponse = gson.fromJson(
                                    response.errorBody().string(),
                                    BaseResponse.class);

                            for (Map.Entry<String, ArrayList<String>> entry : errorResponse.getErrors().entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue().get(0);
                                Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){

                        }
                    }

                }

                @Override
                public void onFailure(Call<Warga> call, Throwable t) {

                }
            });
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.ScreenActivity;
import com.sahitya.banksampahsahitya.admin.HomeAdminActivity;
import com.sahitya.banksampahsahitya.base.fragment.ProfileFragment;
import com.sahitya.banksampahsahitya.coordinator.HomeCoordinatorActivity;
import com.sahitya.banksampahsahitya.model.Role;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.UserResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilActivity extends AppCompatActivity {

    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;

    EditText nama, nope, email, address, password, rt;
    Spinner sex;

    String avatar_location, avatar_type;

    Button btn_update;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mContext = this;

        avatar_location = "";
        avatar_type = "";

        nama = findViewById(R.id.it_nama_p);
        nope = findViewById(R.id.it_nope_p);
        email = findViewById(R.id.it_email_p);
        sex = findViewById(R.id.it_sex_p);
        address = findViewById(R.id.it_address_p);
        password = findViewById(R.id.it_password_p);
        rt = findViewById(R.id.it_rt_p);

        nama.setText(sharedPrefManager.getSPNama());
        nope.setText(sharedPrefManager.getSPMobile());
        email.setText(sharedPrefManager.getSPEmail());
        address.setText(sharedPrefManager.getSpAddress());
        sex.setSelection(getIndex(sex, sharedPrefManager.getSpSex()));
        rt.setText(sharedPrefManager.getSpRt());

        Log.d("sex :", String.valueOf(sex));

        btn_update = findViewById(R.id.btn_edit_profile);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BaseResponse> updateUser = apiInterface.updateUser(
                        sharedPrefManager.getSPToken(),
                        email.getText().toString(),
                        nama.getText().toString(),
                        sex.getSelectedItem().toString(),
                        nope.getText().toString(),
                        rt.getText().toString(),
                        address.getText().toString(),
                        avatar_type.toString(),
                        avatar_location.toString()
                );
                updateUser.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.code() >= 200 && response.code() < 300) {
                            String message = response.body().getMessage();
                            Log.d("pesannya", String.valueOf(response.code()));
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                            sharedPrefManager.saveSPString(SharedPrefManager.SP_ADDRESS, address.getText().toString());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email.getText().toString());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama.getText().toString());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_MOBILE, nope.getText().toString());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_SEX, sex.getSelectedItem().toString());
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_RT, rt.getText().toString());

                        } else {
                            Toast.makeText(mContext, "Ada kesalahan", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

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
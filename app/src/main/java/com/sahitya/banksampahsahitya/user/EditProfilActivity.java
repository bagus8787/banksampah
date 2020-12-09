package com.sahitya.banksampahsahitya.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilActivity extends AppCompatActivity {

    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;

    EditText nama, nope, email, address, password, pass_confirm;
    Spinner sex, rt;

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

        nama = findViewById(R.id.it_nama_p);
        nope = findViewById(R.id.it_nope_p);
        email = findViewById(R.id.it_email_p);
        sex = findViewById(R.id.it_sex_p);
        address = findViewById(R.id.it_address_p);
        password = findViewById(R.id.it_password_p);
        pass_confirm = findViewById(R.id.it_password_confirm_p);
        rt = findViewById(R.id.it_rt_p);

        nama.setHint(sharedPrefManager.getSPNama());
        nope.setHint(sharedPrefManager.getSPMobile());
        email.setHint(sharedPrefManager.getSPEmail());


        boolean hasWarga = sharedPrefManager.hasWarga();

        if (hasWarga) {
            address.setHint(sharedPrefManager.getSpAddress());
            sex.setSelection(getIndex(sex, sharedPrefManager.getSpSex()));
            rt.setSelection(getIndex(rt, sharedPrefManager.getSpRt()));
        } else {
            rt.setVisibility(View.GONE);
            sex.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            findViewById(R.id.lbl_it_gender).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_rt).setVisibility(View.GONE);
            findViewById(R.id.lbl_it_address).setVisibility(View.GONE);
        }

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
                        rt.getSelectedItem().toString(),
                        address.getText().toString(),
                        avatar_location,
                        password.getText().toString(),
                        pass_confirm.getText().toString()
                );
                updateUser.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.code() >= 200 && response.code() < 300) {
                            String message = response.body().getMessage();
                            Log.d("pesannya", String.valueOf(response.code()));
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                            sharedPrefManager.setSpAddress(address.getText().toString());
                            sharedPrefManager.setSpEmail(email.getText().toString());
                            sharedPrefManager.setSpNama(nama.getText().toString());
                            sharedPrefManager.setSpMobile(nope.getText().toString());
                            sharedPrefManager.setSpSex(sex.getSelectedItem().toString());
                            sharedPrefManager.setSpRt(rt.getSelectedItem().toString());

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
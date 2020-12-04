package com.sahitya.banksampahsahitya.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.UserResponse;
import com.sahitya.banksampahsahitya.user.MainActivity;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText nama, no_tlp, email, password, password_confirm, alamat;

    Spinner sex;

    int confirm_agreement = 0;

    Context mContext;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    Button btn_daftar;
    CheckBox checkBox;
    TextView open_term_condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        no_tlp = findViewById(R.id.no_tlp);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);
        sex = findViewById(R.id.sex);
        alamat = findViewById(R.id.alamat);

        btn_daftar = findViewById(R.id.btn_daftar);
        checkBox = findViewById(R.id.checkbox);

        open_term_condition = findViewById(R.id.open_term_dialog);

        open_term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = new WebView(RegisterActivity.this);
                String termText = RegisterActivity.this.getString(R.string.term_condition);
                webView.loadData(termText, "text/html", "utf-8");
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle(R.string.term)
                        .setView(webView)
                        .setNeutralButton("OK", null)
                        .show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    confirm_agreement = 1;
                }
                else {
                    confirm_agreement = 0;
                }
            }
        });

        mContext = this;

        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.sa_login) void sa_login() {
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    @OnClick(R.id.btn_daftar) void signup() {
        progressDialog.show();
        Call<BaseResponse> postRegister = apiInterface.postRegister(
                email.getText().toString(),
                nama.getText().toString(),
                sex.toString(),
                no_tlp.getText().toString(),
                alamat.getText().toString(),
                password_confirm.getText().toString(),
                password.getText().toString(),
                confirm_agreement);
        postRegister.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                progressDialog.dismiss();

                if (response.code() >= 200 && response.code() < 300) {
                    String message = response.body().getMessage();
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    try {
                        Gson gson = new Gson();
                        BaseResponse errorResponse = gson.fromJson(
                                response.errorBody().string(),
                                BaseResponse.class);

                        for (Map.Entry<String, ArrayList<String>> entry : errorResponse.getErrors().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue().get(0);
//                            Toast.makeText(mContext, key.concat(": ").concat(value), Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){

                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


}

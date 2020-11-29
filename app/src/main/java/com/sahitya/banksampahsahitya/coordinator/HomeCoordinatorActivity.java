package com.sahitya.banksampahsahitya.coordinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.Fragment.HomeAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.ProfilAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.ScanAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.UserListAdminFragment;
import com.sahitya.banksampahsahitya.camera.Potrait;
import com.sahitya.banksampahsahitya.coordinator.Fragment.HomeCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ListSampahCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ProfilCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ScanCoordinatorFragment;
import com.sahitya.banksampahsahitya.fragment.baseUser.ProfileFragment;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeCoordinatorActivity extends AppCompatActivity {

    private IntentIntegrator intentIntegrator;
    private SharedPrefManager sharedPrefManager;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_coordinator);
        loadFragment(new HomeCoordinatorFragment());

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_c :
                        loadFragment(new HomeCoordinatorFragment());
                        return true;

                    case R.id.user_list_c :
                        loadFragment(new ListSampahCoordinatorFragment());
                        return true;

                    case R.id.scan_c :
                        intentIntegrator = new IntentIntegrator(HomeCoordinatorActivity.this);
                        intentIntegrator.setCaptureActivity(Potrait.class);
                        intentIntegrator.setCameraId(0);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        intentIntegrator.setPrompt("Scan Barcode Verifikasi");
                        intentIntegrator.initiateScan();
                        return true;

                    case R.id.profile_c :
                        loadFragment(new ProfileFragment());
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void loadFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                String hasil = result.getContents();
                Call<BaseResponse> scanBarcode = apiInterface.scanBarcode(sharedPrefManager.getSPToken(), hasil);

                scanBarcode.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                            Toast.makeText(HomeCoordinatorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeCoordinatorActivity.this, "Error: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
//                            Toast.makeText(HomeCoordinatorActivity.this, "Error: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
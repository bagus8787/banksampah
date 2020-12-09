package com.sahitya.banksampahsahitya.coordinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.Fragment.UserListAdminFragment;
import com.sahitya.banksampahsahitya.base.activity.TukarBarangActivity;
import com.sahitya.banksampahsahitya.camera.Potrait;
import com.sahitya.banksampahsahitya.coordinator.Fragment.HomeCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ListSampahCoordinatorFragment;
import com.sahitya.banksampahsahitya.base.fragment.ProfileFragment;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

public class HomeCoordinatorActivity extends AppCompatActivity {

    private IntentIntegrator intentIntegrator;
    private SharedPrefManager sharedPrefManager;
    private ApiInterface apiInterface;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_coordinator);
        loadFragment(new HomeCoordinatorFragment());

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sharedPrefManager = new SharedPrefManager(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_koor);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_c :
                        loadFragment(new HomeCoordinatorFragment());
                        return true;

                    case R.id.user_list_c :
                        loadFragment(new UserListAdminFragment());
                        return true;

                    case R.id.list_sampah_c :
                        loadFragment(new ListSampahCoordinatorFragment());
                        return true;

                    case R.id.scan_c :
                        intentIntegrator = new IntentIntegrator(HomeCoordinatorActivity.this);
                        intentIntegrator.setCaptureActivity(Potrait.class);
                        intentIntegrator.setCameraId(0);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        intentIntegrator.setPrompt("Scan Barcode Warga");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                String hasil = result.getContents();
                startActivity(new Intent(this, TukarBarangActivity.class).putExtra("IT_BARCODE", hasil));
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
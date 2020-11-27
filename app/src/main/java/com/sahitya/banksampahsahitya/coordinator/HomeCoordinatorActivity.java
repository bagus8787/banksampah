package com.sahitya.banksampahsahitya.coordinator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.Fragment.HomeAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.ProfilAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.ScanAdminFragment;
import com.sahitya.banksampahsahitya.admin.Fragment.UserListAdminFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.HomeCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ListSampahCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ProfilCoordinatorFragment;
import com.sahitya.banksampahsahitya.coordinator.Fragment.ScanCoordinatorFragment;
import com.sahitya.banksampahsahitya.fragment.baseUser.ProfileFragment;

public class HomeCoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_coordinator);
        loadFragment(new HomeCoordinatorFragment());

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
                        loadFragment(new ScanCoordinatorFragment());
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
}
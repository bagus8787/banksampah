package com.sahitya.banksampahsahitya.admin;

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
import com.sahitya.banksampahsahitya.user.Fragment.HistoryFragment;
import com.sahitya.banksampahsahitya.user.Fragment.HomeFragment;
import com.sahitya.banksampahsahitya.user.Fragment.ListFragment;
import com.sahitya.banksampahsahitya.user.Fragment.ProfileFragment;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        loadFragment(new HomeAdminFragment());

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home_admin :
                        loadFragment(new HomeAdminFragment());
                        return true;

                    case R.id.user_list_admin :
                        loadFragment(new UserListAdminFragment());
                        return true;

                    case R.id.scan_admin :
                        loadFragment(new ScanAdminFragment());
                        return true;

                    case R.id.profile_admin :
                        loadFragment(new ProfilAdminFragment());
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
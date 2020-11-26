package com.sahitya.banksampahsahitya.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.user.Fragment.HomeFragment;
import com.sahitya.banksampahsahitya.user.Fragment.InfoSampahFragment;
import com.sahitya.banksampahsahitya.user.Fragment.HistoryFragment;
import com.sahitya.banksampahsahitya.user.Fragment.ListFragment;
import com.sahitya.banksampahsahitya.user.Fragment.PeringkatFragment;
import com.sahitya.banksampahsahitya.user.Fragment.ProfileFragment;
import com.sahitya.banksampahsahitya.user.Fragment.TabunganFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        loadFragment(new HomeFragment());
                        return true;

                    case R.id.list_sampah :
                        loadFragment(new ListFragment());
                        return true;

                    case R.id.navigation_history :
                        loadFragment(new HistoryFragment());
                        return true;

                    case R.id.navigation_profil :
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

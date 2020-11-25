package com.sahitya.banksampahsahitya.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.user.Fragment.InfoSampahFragment;
import com.sahitya.banksampahsahitya.user.Fragment.LaporanFragment;
import com.sahitya.banksampahsahitya.user.Fragment.PeringkatFragment;
import com.sahitya.banksampahsahitya.user.Fragment.ProfileFragment;
import com.sahitya.banksampahsahitya.user.Fragment.TabunganFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new TabunganFragment());

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        loadFragment(new TabunganFragment());
                        return true;

                    case R.id.navigation_in_out :
                        loadFragment(new InfoSampahFragment());
                        return true;

                    case R.id.navigation_tabung :
                        loadFragment(new PeringkatFragment());
                        return true;

                    case R.id.navigation_history :
                        loadFragment(new LaporanFragment());
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

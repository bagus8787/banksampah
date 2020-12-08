package com.sahitya.banksampahsahitya.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.admin.Fragment.HomeAdminFragment;
import com.sahitya.banksampahsahitya.user.Fragment.HistoryFragment;

public class HistoryUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        ActionBar actionBar = getSupportActionBar();

        String nama = getIntent().getStringExtra("IT_NAMA");
        actionBar.setTitle("Transaksi dari " + nama);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.hide();

        loadFragment(new HistoryFragment());

    }

    private void loadFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container_list_user_admin, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
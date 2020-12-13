package com.sahitya.banksampahsahitya.base.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.adapter.AdapterListBarang;

import android.content.Context;
import android.os.Bundle;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;

import java.util.ArrayList;

public class ListSampahActivity extends AppCompatActivity {

    private AdapterListBarang adapter;
    private BarangViewModel viewModel;
    String JENIS;

    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sampah);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Jenis Sampah");
        actionBar.setDisplayHomeAsUpEnabled(true);


        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layout_barang_refresh_user);
        RecyclerView recyclerView = findViewById(R.id.rv_list_barang_user);

        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        mContext = this;

        boolean hasWarga = sharedPrefManager.hasWarga();

//        if (hasWarga){
//
//        }

        JENIS = getIntent().getStringExtra("JENIS");

        adapter = new AdapterListBarang(this);

        viewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
        viewModel.init();
        viewModel.getBarangsResponseLiveData().observe(this, new Observer<ArrayList<Barang>>() {
            @Override
            public void onChanged(ArrayList<Barang> barangResponse) {
                if (barangResponse != null) {
                    adapter.setDataList(barangResponse);
                }
            }
        });

//        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
//                viewModel.getBarangs(spinner.getSelectedItem().toString());
            }
        });

        viewModel.getBarangs(JENIS);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
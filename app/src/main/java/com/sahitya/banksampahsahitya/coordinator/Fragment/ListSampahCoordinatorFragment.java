package com.sahitya.banksampahsahitya.coordinator.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.adapter.AdapterListBarang;
import com.sahitya.banksampahsahitya.base.activity.FormBarangActivity;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;

import java.util.ArrayList;

public class ListSampahCoordinatorFragment extends Fragment {

    private AdapterListBarang adapter;
    private BarangViewModel viewModel;
    Spinner spinner;

    public ListSampahCoordinatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AdapterListBarang(getContext());

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_sampah_coordinator, container, false);

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.layout_barang_refresh);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_list_barang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        spinner = rootView.findViewById(R.id.sp_barang);
        Button barang_create = rootView.findViewById(R.id.barang_create_btn);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                viewModel.getBarangs(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                viewModel.getBarangs("");
            }

        });

        barang_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FormBarangActivity.class)
                        .putExtra("ACTION", "CREATE"));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                viewModel.getBarangs(spinner.getSelectedItem().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getBarangs("");
    }

    @Override
    public void onResume(){
        super.onResume();
        viewModel.getBarangs(spinner.getSelectedItem().toString());
    }
}
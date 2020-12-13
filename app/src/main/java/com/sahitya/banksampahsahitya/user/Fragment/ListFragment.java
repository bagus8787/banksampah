package com.sahitya.banksampahsahitya.user.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.adapter.AdapterListBarang;
import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.viewmodels.BarangViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private AdapterListBarang adapter;
    private BarangViewModel viewModel;

    public ListFragment() {
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.layout_barang_refresh_user);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_list_barang_user);

//        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Spinner spinner = rootView.findViewById(R.id.sp_barang_user);

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

}

package com.sahitya.banksampahsahitya.user.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.sahitya.banksampahsahitya.adapter.AdapterListTransaksi;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.viewmodels.PointHistoryViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTukarFragment extends Fragment {
    private AdapterListTransaksi adapter;
    private PointHistoryViewModel viewModel;

    public ListTukarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AdapterListTransaksi(getContext());

        viewModel = ViewModelProviders.of(this).get(PointHistoryViewModel.class);
        viewModel.init();
        viewModel.getPointsResponseLiveData().observe(this, new Observer<ArrayList<PointHistory>>() {
            @Override
            public void onChanged(ArrayList<PointHistory> pointResponse) {
                if (pointResponse != null) {
                    adapter.setDataList(pointResponse);
                }
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_ambil_saldo, container, false);

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.layoutRefresh);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_list_point);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reloadPoint();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadPoint();
    }

    public void reloadPoint() {
        viewModel.getPoints("tukar");
    }

}

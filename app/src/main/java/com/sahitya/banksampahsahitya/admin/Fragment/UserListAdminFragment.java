package com.sahitya.banksampahsahitya.admin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;

import com.sahitya.banksampahsahitya.Adapter.AdapterListUser;
import com.sahitya.banksampahsahitya.repositories.PointRepository;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.UserViewModel;
import java.util.ArrayList;

import android.util.Log;

public class UserListAdminFragment extends Fragment {
    private AdapterListUser adapter;
    private UserViewModel viewModel;
    SharedPrefManager sharedPrefManager;
    PointRepository pointRepository;

    public UserListAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AdapterListUser(getContext());
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init();
        viewModel.getUserResponseLiveData().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userListResponse) {
                if (userListResponse != null) {
                    adapter.setDataList(userListResponse);

                    Log.d("hasile : ", String.valueOf(adapter));
                }
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        pointRepository = new PointRepository();
        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.layoutRefresh);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_list_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reloadUserList();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadUserList();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            actionBar.setTitle("Total Saldo Rp. " + sharedPrefManager.getWargaPointTotal().toString());
            actionBar.show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getUserList();
    }

    public void reloadUserList() {
        viewModel.getUserList();
        pointRepository.getAdminPoints();
    }
}
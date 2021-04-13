package com.sahitya.banksampahsahitya.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahitya.banksampahsahitya.Adapter.AdapterListUser;
import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.R;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.repositories.PointRepository;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;
import com.sahitya.banksampahsahitya.viewmodels.UserViewModel;

import java.util.ArrayList;

import static com.sahitya.banksampahsahitya.MyApp.getContext;

public class ListUserRtActivity extends AppCompatActivity {

    private AdapterListUser adapter;
    private UserViewModel viewModel;
    SharedPrefManager sharedPrefManager;

    String rt, point_rt;

    TextView txt_total_warga, txt_point_admin, txt_rt, txt_no_user;
    RelativeLayout rv_no_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_rt);

        ActionBar actionBar = getSupportActionBar();

        rt = getIntent().getStringExtra("rt");
        point_rt = getIntent().getStringExtra("point_rt");

        txt_rt          = findViewById(R.id.txt_rt);
        txt_point_admin = findViewById(R.id.txt_point_admin);
        txt_total_warga = findViewById(R.id.txt_total_warga);
        txt_no_user     = findViewById(R.id.txt_no_user);

        rv_no_user      = findViewById(R.id.rv_no_user);

        txt_rt.setText(rt);
        txt_point_admin.setText("Rp. " + point_rt);

        actionBar.setTitle("List Warga RT " + rt);
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new AdapterListUser(getContext());
        sharedPrefManager = new SharedPrefManager(getContext());

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init();
        viewModel.getUserResponseLiveData().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userListResponse) {
                if (userListResponse != null) {

                    adapter.setDataList(userListResponse);
                    txt_total_warga.setText(String.valueOf(adapter.getItemCount()));

                    if (adapter.getItemCount() != 0){
                        txt_no_user.setVisibility(View.INVISIBLE);

                    } else {
                        txt_no_user.setVisibility(View.VISIBLE);

                    }

                    Log.d("adapter", String.valueOf(adapter.getItemCount()));

                }
            }
        });

//        pointRepository = new PointRepository();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.layoutRefresh);
        RecyclerView recyclerView = findViewById(R.id.rv_list_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                reloadUserList();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getUserByRt(rt);
    }

    public void reloadUserList() {
        viewModel.getUserByRt(rt);
//        pointRepository.getAdminPoints();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
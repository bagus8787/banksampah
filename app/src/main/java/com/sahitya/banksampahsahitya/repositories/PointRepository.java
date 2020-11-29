package com.sahitya.banksampahsahitya.repositories;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointRepository {
    private MutableLiveData<ArrayList<PointHistory>> pointResponseLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PointRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        pointResponseLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPoints(String type) {
        Call<ArrayList<PointHistory>> getUserTransaksi = apiInterface.getUserTransaksi(sharedPrefManager.getSPToken(), type);
        getUserTransaksi.enqueue(new Callback<ArrayList<PointHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<PointHistory>> call, Response<ArrayList<PointHistory>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PointHistory>> call, Throwable t) {
                pointResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<ArrayList<PointHistory>> getPointResponseLiveData() {
        return pointResponseLiveData;
    }
}

package com.sahitya.banksampahsahitya.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import com.sahitya.banksampahsahitya.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {
    private MutableLiveData<ArrayList<User>> pointResponseLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public UserListRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        pointResponseLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPoints(String type) {
        Call<ArrayList<User>> getUserList = apiInterface.getUserList(sharedPrefManager.getSPToken(), type);
        getUserList.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                pointResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<ArrayList<User>> getPointResponseLiveData() {
        return pointResponseLiveData;
    }
}

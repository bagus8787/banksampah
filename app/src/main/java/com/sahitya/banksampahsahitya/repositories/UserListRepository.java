package com.sahitya.banksampahsahitya.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import com.sahitya.banksampahsahitya.model.User;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListRepository {
    private MutableLiveData<ArrayList<User>> userResponseLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public UserListRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        userResponseLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getUserList() {
        Call<ArrayList<User>> getUserList = apiInterface.getUserList(sharedPrefManager.getSPToken());
        getUserList.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    userResponseLiveData.postValue(response.body());
                    Log.d("vsdvsdvsdv :" , String.valueOf(userResponseLiveData));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                userResponseLiveData.postValue(null);
            }
        });
    }

    public void setUserRole(Integer id, String role_name) {
        Call<BaseResponse> setUserRole = apiInterface.setUserRole(sharedPrefManager.getSPToken(), id, role_name);
        setUserRole.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<User>> getUserResponseLiveData() {
        return userResponseLiveData;
    }
}

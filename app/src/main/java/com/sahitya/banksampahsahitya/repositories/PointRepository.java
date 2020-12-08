package com.sahitya.banksampahsahitya.repositories;

import android.util.Log;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointRepository {
    private MutableLiveData<ArrayList<PointHistory>> pointsLiveData;
    private MutableLiveData<PointHistory> pointLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public PointRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        pointsLiveData = new MutableLiveData<>();
        pointLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPoints(String type) {
        Call<ArrayList<PointHistory>> getUserPoints = apiInterface.getUserPoints(sharedPrefManager.getSPToken(), type);
        getUserPoints.enqueue(new Callback<ArrayList<PointHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<PointHistory>> call, Response<ArrayList<PointHistory>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PointHistory>> call, Throwable t) {
                pointsLiveData.postValue(null);
            }
        });
    }

    public void getPoint(int trx_id) {
        Call<PointHistory> getUserPoint = apiInterface.getUserPoint(sharedPrefManager.getSPToken(), trx_id);
        getUserPoint.enqueue(new Callback<PointHistory>() {
            @Override
            public void onResponse(Call<PointHistory> call, Response<PointHistory> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PointHistory> call, Throwable t) {
                pointLiveData.postValue(null);
            }
        });
    }

    public void getTransaksi(String barcode) {
        Call<PointHistory> getByBarcode = apiInterface.getByBarcode(sharedPrefManager.getSPToken(), barcode);
        getByBarcode.enqueue(new Callback<PointHistory>() {
            @Override
            public void onResponse(Call<PointHistory> call, Response<PointHistory> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PointHistory> call, Throwable t) {
                pointLiveData.postValue(null);
            }
        });
    }

    public void transaksiVerify(String barcode) {
        Call<BaseResponse> verifyBarcode = apiInterface.verifyBarcode(sharedPrefManager.getSPToken(), barcode);
        verifyBarcode.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    Log.d("Point", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public void getWargaPoints(Integer warga_id, String point_type) {
        Call<Warga> getWargaById = apiInterface.getWargaById(sharedPrefManager.getSPToken(), warga_id, point_type);
        getWargaById.enqueue(new Callback<Warga>() {
            @Override
            public void onResponse(Call<Warga> call, Response<Warga> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    pointsLiveData.postValue(response.body().getPoints());
                }
            }

            @Override
            public void onFailure(Call<Warga> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<PointHistory>> getPointsResponseLiveData() {
        return pointsLiveData;
    }

    public LiveData<PointHistory> getPointResponseLiveData() {
        return pointLiveData;
    }
}

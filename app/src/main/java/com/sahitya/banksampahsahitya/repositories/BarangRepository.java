package com.sahitya.banksampahsahitya.repositories;

import android.util.Log;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.Barang;
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

public class BarangRepository {
    private MutableLiveData<ArrayList<Barang>> barangsLiveData;
    private MutableLiveData<Barang> barangLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public BarangRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        barangsLiveData = new MutableLiveData<>();
        barangLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getBarangType() {
        Call<ArrayList<String>> getBarangType = apiInterface.getBarangTypes();
        getBarangType.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    public void getBarangs(String type) {
        Call<ArrayList<Barang>> getBarangList = apiInterface.getBarangList(sharedPrefManager.getSPToken(), type.toLowerCase());
        getBarangList.enqueue(new Callback<ArrayList<Barang>>() {
            @Override
            public void onResponse(Call<ArrayList<Barang>> call, Response<ArrayList<Barang>> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    barangsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Barang>> call, Throwable t) {
                barangsLiveData.postValue(null);
            }
        });
    }

    public void getBarang(int id) {
        Call<Barang> getBarang = apiInterface.getBarang(sharedPrefManager.getSPToken(), id);
        getBarang.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    barangLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                barangLiveData.postValue(null);
            }
        });
    }

    public void storeBarang(String name, Integer point, String type) {
        Call<BaseResponse> storeBarang = apiInterface.storeBarang(sharedPrefManager.getSPToken(), name, point, type.toLowerCase());
        storeBarang.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    Barang barang = new Barang();
                    barang.setName(name);
                    barang.setPoint(point);
                    barang.setType(type);
                    barangLiveData.setValue(barang);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public void updateBarang(Integer id, String name, Integer point, String type) {
        Call<BaseResponse> updateBarang = apiInterface.updateBarang(sharedPrefManager.getSPToken(), id, name, point, type.toLowerCase());
        updateBarang.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    Barang barang = new Barang();
                    barang.setName(name);
                    barang.setPoint(point);
                    barang.setType(type);
                    barangLiveData.setValue(barang);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    public void deleteBarang(Integer id) {
        Call<BaseResponse> deleteBarang = apiInterface.deleteBarang(sharedPrefManager.getSPToken(), id);
        deleteBarang.enqueue(new Callback<BaseResponse>() {
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

    public LiveData<ArrayList<Barang>> getBarangsResponseLiveData() {
        return barangsLiveData;
    }

    public LiveData<Barang> getBarangResponseLiveData() {
        return barangLiveData;
    }
}

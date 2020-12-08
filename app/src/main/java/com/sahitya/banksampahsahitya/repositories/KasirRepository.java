package com.sahitya.banksampahsahitya.repositories;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KasirRepository {
    private MutableLiveData<Warga> wargaLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public KasirRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        wargaLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void tukarBarang(Integer id, Integer barang, Float count) {
        Call<BaseResponse> tukarBarang = apiInterface.tukarBarang(sharedPrefManager.getSPToken(), id, barang, count);
        tukarBarang.enqueue(new Callback<BaseResponse>() {
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

    public void getWargaByBardode(String barcode) {
        Call<Warga> getWargaByBarcode = apiInterface.getWargaByBarcode(sharedPrefManager.getSPToken(), barcode);
        getWargaByBarcode.enqueue(new Callback<Warga>() {
            @Override
            public void onResponse(Call<Warga> call, Response<Warga> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    wargaLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Warga> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<Warga> getWargaResponseLiveData() {
        return wargaLiveData;
    }
}

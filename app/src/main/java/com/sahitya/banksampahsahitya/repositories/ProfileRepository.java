package com.sahitya.banksampahsahitya.repositories;

import android.util.Log;
import android.view.View;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private MutableLiveData<User> profileLiveData;
    private MutableLiveData<BarcodeImage> barcodeLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public ProfileRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        profileLiveData = new MutableLiveData<>();
        barcodeLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getProfile() {
        Call<User> getUser = apiInterface.getUser(sharedPrefManager.getSPToken());
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    profileLiveData.postValue(response.body());
                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_HAS_WARGA, response.body().getWarga() != null);
                    Log.d("HasWarga", String.valueOf(response.body().getWarga() != null));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getBarcode() {
        Call<BaseResponse> getMyBarcode = apiInterface.getMyBarcode(sharedPrefManager.getSPToken());
        getMyBarcode.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    BarcodeImage barcode = new BarcodeImage(response.body().getMessage());
                    barcodeLiveData.postValue(barcode);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void ambilPoint(Integer point) {
        Call<BaseResponse> ambilPoint = apiInterface.ambilPoint(sharedPrefManager.getSPToken(), point);
        ambilPoint.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public LiveData<User> getProfileResponseLiveData() {
        return profileLiveData;
    }

    public LiveData<BarcodeImage> getBarcodeResponseLiveData() {
        return barcodeLiveData;
    }
}

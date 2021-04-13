package com.sahitya.banksampahsahitya.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sahitya.banksampahsahitya.MyApp;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.ApiClient;
import com.sahitya.banksampahsahitya.network.ApiInterface;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.ByRtResponse;
import com.sahitya.banksampahsahitya.utils.SharedPrefManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRtRepository {
    private MutableLiveData<HashMap<String, Integer>> listrtLiveData;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;

    public ListRtRepository() {
        sharedPrefManager = new SharedPrefManager(MyApp.getContext());
        listrtLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getListRt() {
        Call<ByRtResponse> getRt = apiInterface.getSummaryRT(sharedPrefManager.getSPToken());
        getRt.enqueue(new Callback<ByRtResponse>() {
            @Override
            public void onResponse(Call<ByRtResponse> call, Response<ByRtResponse> response) {
                if (response.code() >= 200 && response.code() < 300 && response.body() != null) {
                    Log.d("responnya", "== " + response.body().toString());
//                    HashMap<String, Integer> test = new HashMap<String, Integer>();
//                    test = response.body().getRt_point_totals();
//
//                    for (String rt : test.keySet()){
//                        Log.d("rtsss", "rt nya :" + rt);
//                    }
//
//                    for (Integer point : test.values()){
//                        Log.d("pointsss", "pointnya :" + point);
//                    }
                }

            }

            @Override
            public void onFailure(Call<ByRtResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<HashMap<String, Integer>> getListRtResponseLiveData() {
        return listrtLiveData;
    }

}

package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.response.ByRtResponse;
import com.sahitya.banksampahsahitya.repositories.ListRtRepository;
import com.sahitya.banksampahsahitya.repositories.UserListRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class ListRtVieModel extends AndroidViewModel {
    private ListRtRepository listRtRepository;
    private LiveData<HashMap<String, Integer>> byRtResponseLiveData;

    public ListRtVieModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        listRtRepository = new ListRtRepository();
        byRtResponseLiveData = listRtRepository.getListRtResponseLiveData();
    }

    public void getListRtt() {
        listRtRepository.getListRt();
    }

    public LiveData<HashMap<String, Integer>> getUserResponseLiveData() {
        return byRtResponseLiveData;
    }
}

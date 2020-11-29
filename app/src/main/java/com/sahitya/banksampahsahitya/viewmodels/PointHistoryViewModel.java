package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.repositories.PointRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PointHistoryViewModel extends AndroidViewModel {
    private PointRepository pointRepository;
    private LiveData<ArrayList<PointHistory>> pointResponseLiveData;

    public PointHistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        pointRepository = new PointRepository();
        pointResponseLiveData = pointRepository.getPointResponseLiveData();
    }

    public void getPoints(String type) {
        pointRepository.getPoints(type);
    }

    public LiveData<ArrayList<PointHistory>> getPointResponseLiveData() {
        return pointResponseLiveData;
    }
}

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
    private LiveData<ArrayList<PointHistory>> pointsLivedata;
    private LiveData<PointHistory> pointLiveData;

    public PointHistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        pointRepository = new PointRepository();
        pointsLivedata = pointRepository.getPointsResponseLiveData();
        pointLiveData = pointRepository.getPointResponseLiveData();
    }

    public void getPoints(String type) {
        pointRepository.getPoints(type);
    }

    public void getPoint(int trx_id) {
        pointRepository.getPoint(trx_id);
    }

    public void getTransaksi(String barcode) {
        pointRepository.getTransaksi(barcode);
    }

    public void transaksiVerify(String barcode) {
        pointRepository.transaksiVerify(barcode);
    }

    public void getWargaPoints(Integer warga_id, String point_type){
        pointRepository.getWargaPoints(warga_id, point_type);
    }

    public LiveData<ArrayList<PointHistory>> getPointsResponseLiveData() {
        return pointsLivedata;
    }

    public LiveData<PointHistory> getPointResponseLiveData() {
        return pointLiveData;
    }
}

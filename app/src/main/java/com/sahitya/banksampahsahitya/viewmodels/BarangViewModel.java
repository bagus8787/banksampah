package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.repositories.BarangRepository;
import com.sahitya.banksampahsahitya.repositories.PointRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BarangViewModel extends AndroidViewModel {
    private BarangRepository barangRepository;
    private LiveData<ArrayList<Barang>> barangsLivedata;
    private LiveData<Barang> barangLiveData;

    public BarangViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        barangRepository = new BarangRepository();
        barangsLivedata = barangRepository.getBarangsResponseLiveData();
        barangLiveData = barangRepository.getBarangResponseLiveData();
    }

    public void getBarangs(String type) {
        barangRepository.getBarangs(type);
    }

    public void getBarang(int id) {
        barangRepository.getBarang(id);
    }

    public void storeBarang(String name, Integer point, String type) {
        barangRepository.storeBarang(name, point, type);
    }

    public void updateBarang(Integer id, String name, Integer point, String type) {
        barangRepository.updateBarang(id, name, point, type);
    }

    public void deleteBarang(Integer id) {
        barangRepository.deleteBarang(id);
    }

    public LiveData<ArrayList<Barang>> getBarangsResponseLiveData() {
        return barangsLivedata;
    }

    public LiveData<Barang> getBarangResponseLiveData() {
        return barangLiveData;
    }
}

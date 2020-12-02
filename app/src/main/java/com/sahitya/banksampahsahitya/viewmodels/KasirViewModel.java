package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.repositories.KasirRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class KasirViewModel extends AndroidViewModel {
    private KasirRepository kasirRepository;
    private LiveData<Warga> wargaLiveData;

    public KasirViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        kasirRepository = new KasirRepository();
        wargaLiveData = kasirRepository.getWargaResponseLiveData();
    }

    public void getWargaByBarcode(String barcode) {
        kasirRepository.getWargaByBardode(barcode);
    }


    public LiveData<Warga> getWargaResponseLiveData() {
        return wargaLiveData;
    }
}

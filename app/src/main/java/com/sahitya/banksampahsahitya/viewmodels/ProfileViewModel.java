package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.repositories.ProfileRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<User> profileLiveData;
    private LiveData<BarcodeImage> barcodeLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        profileRepository = new ProfileRepository();
        profileLiveData = profileRepository.getProfileResponseLiveData();
        barcodeLiveData = profileRepository.getBarcodeResponseLiveData();
    }

    public void getProfile() {
        profileRepository.getProfile();
    }

    public void getBarcode() {
        profileRepository.getBarcode();
    }

    public LiveData<User> getProfileResponseLiveData() {
        return profileLiveData;
    }

    public LiveData<BarcodeImage> getBarcodeResponseLiveData() {
        return barcodeLiveData;
    }
}

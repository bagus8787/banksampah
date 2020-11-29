package com.sahitya.banksampahsahitya.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.repositories.PointRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.repositories.UserListRepository;

import java.util.ArrayList;

public class UserViewModel extends AndroidViewModel{
    private UserListRepository userListRepository;
    private LiveData<ArrayList<User>> pointResponseLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        userListRepository = new UserListRepository();
        pointResponseLiveData = userListRepository.getPointResponseLiveData();
    }

    public void getPoints(String type) {
        userListRepository.getPoints(type);
    }

    public LiveData<ArrayList<User>> getPointResponseLiveData() {
        return pointResponseLiveData;
    }
}

package com.sahitya.banksampahsahitya.network.response;

import android.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdminResponse {
    @Expose
    @SerializedName("warga") Integer warga;
    @Expose
    @SerializedName("admin") Integer admin;

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getWarga() {
        return warga;
    }

    public void setWarga(Integer warga) {
        this.warga = warga;
    }
}

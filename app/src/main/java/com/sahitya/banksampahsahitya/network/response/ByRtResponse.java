package com.sahitya.banksampahsahitya.network.response;

import android.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ByRtResponse {
    @Expose
    @SerializedName("rt_point_totals")
    HashMap<String, Integer> rt_point_totals;

    public HashMap<String, Integer> getRt_point_totals() {
        return rt_point_totals;
    }

    public void setRt_point_totals(HashMap<String, Integer> rt_point_totals) {
        this.rt_point_totals = rt_point_totals;
    }
}

package com.sahitya.banksampahsahitya.network.response;

import android.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseResponse {
    @Expose
    @SerializedName("errors")
    ArrayMap<String, ArrayList<String>> errors;
    @Expose
    @SerializedName("message") String message;

    public ArrayMap<String, ArrayList<String>> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }
}

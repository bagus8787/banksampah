package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Warga {

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("address") String address;
    @Expose
    @SerializedName("sex") String sex;
    @Expose
    @SerializedName("rt") String rt;
    @Expose
    @SerializedName("point_total") int point_total;

    @Expose
    @SerializedName("points")
    ArrayList<PointHistory> points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public int getPointTotal() {
        return point_total;
    }

    public void setPointTotal(int point_total) {
        this.point_total = point_total;
    }

    public ArrayList<PointHistory> getPoints() {
        return points;
    }
}


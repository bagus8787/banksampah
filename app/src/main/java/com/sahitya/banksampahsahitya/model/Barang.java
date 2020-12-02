package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barang {
    public final static String TYPE_PLASTIK = "plastik";
    public final static String TYPE_KERTAS = "kertas";
    public final static String TYPE_LOGAM = "logam";
    public final static String TYPE_KACA = "kaca";
    public final static String TYPE_LAIN = "lain";

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("name") String name;
    @Expose
    @SerializedName("point") Integer point;
    @Expose
    @SerializedName("type") String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

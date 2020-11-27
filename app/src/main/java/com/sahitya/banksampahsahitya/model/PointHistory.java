package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointHistory {

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("type") String type;
    @Expose
    @SerializedName("count") int count;
    @Expose
    @SerializedName("description") String description;
    @Expose
    @SerializedName("point_total") int point_total;
    @Expose
    @SerializedName("barcode") String barcode;
    @Expose
    @SerializedName("verified") boolean verified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPointTotal() {
        return point_total;
    }

    public void setPointTotal(int point_total) {
        this.point_total = point_total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public boolean isVerified() {
        return verified;
    }
}

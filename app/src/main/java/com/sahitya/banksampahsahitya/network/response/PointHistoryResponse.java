package com.sahitya.banksampahsahitya.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sahitya.banksampahsahitya.model.BarcodeImage;
import com.sahitya.banksampahsahitya.model.Warga;

public class PointHistoryResponse {

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("type") String type;
    @Expose
    @SerializedName("count") int count;
    @Expose
    @SerializedName("description") String description;
    @Expose
    @SerializedName("point") int point;
    @Expose
    @SerializedName("point_total") int point_total;
    @Expose
    @SerializedName("barcode") String barcode;
    @Expose
    @SerializedName("verified") boolean verified;
    @Expose
    BarcodeImage barcode_image;
    @Expose
    @SerializedName("warga")
    Warga warga;

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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public BarcodeImage getBarcodeImage() {
        if (barcode_image == null) {
            this.barcode_image = new BarcodeImage(barcode);
        }
        return barcode_image;
    }

    public void setBarcodeImage(BarcodeImage barcode) {
        this.barcode_image = barcode_image;
    }

    public boolean isVerified() {
        return verified;
    }

    public Warga getWarga() { return warga;}

    public void setWarga(Warga warga) {this.warga = warga;}
}

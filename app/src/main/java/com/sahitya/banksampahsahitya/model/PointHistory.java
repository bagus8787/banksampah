package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointHistory {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PRINT_DATE_FORMAT = "HH:mm:ss dd-MM-yyy";

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("type") String type;

    @Expose
    @SerializedName("created_at") String created_at;

    @Expose
    @SerializedName("updated_at") String updated_at;

    @Expose
    @SerializedName("count") Float count;
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

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getCreatedAtFormatted() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String formatted = "";
        try {
            Date date = format.parse(created_at);
            SimpleDateFormat newformat = new SimpleDateFormat(PRINT_DATE_FORMAT);
            String dateTime = newformat.format(date);
            formatted = dateTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public String getUpdatedAtFormatted() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String formatted = "";
        try {
            Date date = format.parse(updated_at);
            SimpleDateFormat newformat = new SimpleDateFormat(PRINT_DATE_FORMAT);
            String dateTime = newformat.format(date);
            formatted = dateTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
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

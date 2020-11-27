package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {
    public final static String ROLE_USER = "warga";
    public final static String ROLE_COODINATOR = "koordinator";
    public final static String ROLE_ADMIN = "admin";

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("name") String name;

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
}

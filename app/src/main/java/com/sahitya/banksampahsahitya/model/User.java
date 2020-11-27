package com.sahitya.banksampahsahitya.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @Expose
    @SerializedName("id") int id;
    @Expose
    @SerializedName("name") String name;
    @Expose
    @SerializedName("mobile") String mobile;
    @Expose
    @SerializedName("email") String email;
    @Expose
    @SerializedName("warga") Warga warga;

    @Expose
    @SerializedName("roles")
    ArrayList<Role> roles;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return  this.roles != null ? this.roles.get(0).getName() : "";
    }

    public boolean isAdmin() {
        return !this.getRoleName().equals("") && this.getRoleName().equals(Role.ROLE_ADMIN);
    }

    public boolean isCoordinator() {
        return !this.getRoleName().equals("") && this.getRoleName().equals(Role.ROLE_COODINATOR);
    }

    public boolean isUser() {
        return !this.getRoleName().equals("") && this.getRoleName().equals(Role.ROLE_USER);
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public Warga getWarga() {
        return warga;
    }
}


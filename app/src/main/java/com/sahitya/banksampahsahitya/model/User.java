package com.sahitya.banksampahsahitya.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User implements Parcelable {

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

    public User(ArrayList<User> dataList){
    }

    public User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mobile = in.readString();
        email = in.readString();
    }

    public User(int id, String name, String mobile, String email) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(email);
    }
}


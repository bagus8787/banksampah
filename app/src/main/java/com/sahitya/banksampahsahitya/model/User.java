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
    @SerializedName("address") String address;
    @Expose
    @SerializedName("warga") Warga warga;
    @Expose
    @SerializedName("sex") String sex;
    @Expose
    @SerializedName("rt") String rt;
    @Expose
    @SerializedName("point_total") int point_total;
    @Expose
    @SerializedName("roles")
    ArrayList<Role> roles;

    private boolean showMenu = false;

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

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
        return getRoleName().equals(Role.ROLE_ADMIN);
    }

    public boolean isCoordinator() {
        return getRoleName().equals(Role.ROLE_COODINATOR);
    }

    public boolean isUser() {
        return getRoleName().equals(Role.ROLE_USER);
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


package com.sahitya.banksampahsahitya.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sahitya.banksampahsahitya.model.Token;
import com.sahitya.banksampahsahitya.model.User;

public class UserResponse extends BaseResponse {

    @Expose
    @SerializedName("status") String status;
    @Expose
    @SerializedName("token")
    Token token;
    @Expose
    @SerializedName("expires_in") int expires_in;
    @Expose
    @SerializedName("user")
    User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token.getAccess_token();
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

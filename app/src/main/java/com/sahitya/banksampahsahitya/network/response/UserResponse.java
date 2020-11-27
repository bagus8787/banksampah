package com.sahitya.banksampahsahitya.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sahitya.banksampahsahitya.model.Token;
import com.sahitya.banksampahsahitya.model.User;

public class UserResponse extends BaseResponse {

    @Expose
    @SerializedName("token")
    Token token;
    @Expose
    @SerializedName("user")
    User user;

    public String getToken() {
        return token.getAccess_token();
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

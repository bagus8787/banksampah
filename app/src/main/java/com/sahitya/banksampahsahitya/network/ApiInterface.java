package com.sahitya.banksampahsahitya.network;

import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UserResponse> postLogin(@Field("email") String email,
                                 @Field("password") String password);
    @POST("api/auth/refresh")
    Call<UserResponse> refreshToken(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/auth/signup")
    Call<BaseResponse> postRegister(
            @Field("email") String email,
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("password_confirmation") String password_confirmation,
            @Field("password") String password,
            @Field("confirm_agreement") int confirm_agreement);

    //mahasiswa
    @GET("api/home")
    Call<User> getUser(@Header("Authorization") String token);

    @GET("api/admin/warga")
    Call<ArrayList<User>> getUserList(String spToken, @Header("Authorization") String token);

    @GET("api/home/transaksi")
    Call<ArrayList<PointHistory>> getUserTransaksi(@Header("Authorization") String token, @Query("type") String type);

    @FormUrlEncoded
    @POST("api/kasir/scan")
    Call<BaseResponse> scanBarcode(@Header("Authorization") String token, @Field("barcode") String barcode);

}

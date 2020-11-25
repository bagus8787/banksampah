package com.sahitya.banksampahsahitya.network;

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
//    @GET("api/home")
//    Call<Bimbingan> getUser(@Header("Authorization") String token);
//
//    @Multipart
//    @POST("api/home")
//    Call<BaseResponse> updateData(@Header("Authorization") String token,
//                                  @Part MultipartBody.Part filename,
//                                  @Part("status") RequestBody status);
//
//    //dosen
//    @GET("api/bimbingan")
//    Call<ArrayList<BimbinganResponse>> getBimbinganList(@Header("Authorization") String token);
//
//    @GET("api/bimbingan/{id}")
//    Call<BimbinganResponse> getBimbingan(@Header("Authorization") String token, @Path("id") int id);

    @Multipart
    @POST("api/bimbingan/revisi/{id}")
    Call<BaseResponse> revisiBimbingan(@Header("Authorization") String token,
                                       @Path("id") int id,
                                       @Part MultipartBody.Part filename,
                                       @Part("keterangan") RequestBody keterangan,
                                       @Part("status") RequestBody status);

    @Multipart
    @POST("api/bimbingan/revisi/{id}")
    Call<BaseResponse> setujuiBimbingan(@Header("Authorization") String token,
                                        @Path("id") int id,
                                        @Part("keterangan") RequestBody keterangan,
                                        @Part("status") RequestBody status);

}

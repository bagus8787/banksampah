package com.sahitya.banksampahsahitya.network;

import com.sahitya.banksampahsahitya.model.Barang;
import com.sahitya.banksampahsahitya.model.PointHistory;
import com.sahitya.banksampahsahitya.model.User;
import com.sahitya.banksampahsahitya.model.Warga;
import com.sahitya.banksampahsahitya.network.response.AdminResponse;
import com.sahitya.banksampahsahitya.network.response.BaseResponse;
import com.sahitya.banksampahsahitya.network.response.ByRtResponse;
import com.sahitya.banksampahsahitya.network.response.UserResponse;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UserResponse> postLogin(@Field("email") String email,
                                 @Field("password") String password);

    @Headers({"Accept: application/json"})
    @POST("api/auth/refresh")
    Call<UserResponse> refreshToken(@Header("Authorization") String token);

    @GET("api/auth/logout")
    Call<BaseResponse> logout(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
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

    //user
    @GET("api/home")
    Call<User> getUser(@Header("Authorization") String token);

    @GET("api/auth/barcode")
    Call<BaseResponse> getMyBarcode(@Header("Authorization") String token);

    @Multipart
    @POST("api/home/update_profile")
    Call<BaseResponse> updateUser(@Header("Authorization") String token,
                @Part("email") RequestBody email,
                @Part("name") RequestBody name,
                @Part("sex") RequestBody sex,
                @Part("mobile") RequestBody mobile,
                @Part("rt") RequestBody rt,
                @Part("address") RequestBody address,
                @Part MultipartBody.Part avatar_location,
                @Part("password") RequestBody password,
                @Part("password_confirmation") RequestBody pass_confirmation);

    // Warga
    @GET("api/admin/warga/{id}")
    Call<Warga> getWargaById(@Header("Authorization") String token, @Path("id") Integer id, @Query("type") String point_type);

    //updateUser
    @FormUrlEncoded
    @POST("api/admin/warga/{id}")
    Call<Warga> updateWarga(@Header("Authorization") String token, @Path("id") Integer id,
            @Field("name") String username,
            @Field("address") String address,
            @Field("mobile") String mobile,
            @Field("sex") String sex,
            @Field("email") String email,
            @Field("rt") String rt,
            @Field("password") String password,
            @Field("password_confirmation") String pass_confirmation);
    
    //user list
    @GET("api/admin/warga")
    Call<ArrayList<User>> getUserList(@Header("Authorization") String token);

    @GET("api/home/transaksi")
    Call<ArrayList<PointHistory>> getUserPoints(@Header("Authorization") String token, @Query("type") String type);

    @GET("api/home/transaksi/{id}")
    Call<PointHistory> getUserPoint(@Header("Authorization") String token, @Field("id") int id);

//    kasir
    @FormUrlEncoded
    @POST("api/kasir/scan")
    Call<PointHistory> getByBarcode(@Header("Authorization") String token, @Field("barcode") String barcode);

    @FormUrlEncoded
    @POST("api/kasir/verify")
    Call<BaseResponse> verifyBarcode(@Header("Authorization") String token, @Field("barcode") String barcode);

    @FormUrlEncoded
    @POST("api/kasir/tukar_barang/{id}")
    Call<BaseResponse> tukarBarang(@Header("Authorization") String token, @Path("id") Integer id,
                                   @Field("barang") Integer barang,
                                   @Field("count") Float count);
    @FormUrlEncoded
    @POST("api/kasir/scan_warga")
    Call<Warga> getWargaByBarcode(@Header("Authorization") String token, @Field("barcode") String barcode);


//    barang
    @GET("api/config/barang_type")
    Call<ArrayList<String>> getBarangTypes();

    @GET("api/admin/barang")
    Call<ArrayList<Barang>> getBarangList(@Header("Authorization") String token, @Query("type") String type);

    @FormUrlEncoded
    @POST("api/admin/barang")
    Call<BaseResponse> storeBarang(@Header("Authorization") String token,
                                        @Field("name") String name,
                                        @Field("point") Integer point,
                                        @Field("type") String type);

    @GET("api/admin/barang/{id}")
    Call<Barang> getBarang(@Header("Authorization") String token, @Path("id") Integer id);

    @FormUrlEncoded
    @POST("api/admin/barang/{id}")
    Call<BaseResponse> updateBarang(@Header("Authorization") String token, @Path("id") Integer id,
                              @Field("name") String name,
                              @Field("point") Integer point,
                              @Field("type") String type);

    @DELETE("api/admin/barang/{id}")
    Call<BaseResponse> deleteBarang(@Header("Authorization") String token, @Path("id") Integer id);

    @FormUrlEncoded
    @POST("api/home/ambil_point")
    Call<BaseResponse> ambilPoint(@Header("Authorization") String token, @Field("point") Integer point);

    @POST("api/admin/warga/{id}/as_role/{role}")
    Call<BaseResponse> setUserRole(@Header("Authorization") String token, @Path("id") Integer id, @Path("role") String role_name);

    @GET("api/admin/points")
    Call<AdminResponse> getAdminPoints(@Header("Authorization") String token);

    @GET("api/admin/warga/summary")
    Call<ByRtResponse> getSummaryRT(@Header("Authorization") String token);

//    @GET("api/admin/warga?rt=")
//    Call<ByRtResponse> getByRT(@Header("Authorization") String token, @Query("rt") String rt);

    // Warga
    @GET("api/admin/warga?rt=")
    Call<ArrayList<User>> getWargaByRT(@Header("Authorization") String token, @Query("rt") String rt);

}

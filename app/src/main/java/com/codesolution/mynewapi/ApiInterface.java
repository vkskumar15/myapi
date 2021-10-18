package com.codesolution.mynewapi;

import com.codesolution.mynewapi.Models.LoginModel;
import com.codesolution.mynewapi.Models.RegisterModel;
import com.codesolution.mynewapi.Models.UniqueUserNameModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("uniqueApi")
    Call<UniqueUserNameModel> sendUniqueData(
            @Field("username") String username
    );



    @Multipart
    @POST("register")
    Call<RegisterModel> RegisterUser(
            @Part("username")RequestBody username,
            @Part("password")RequestBody password,
            @Part("joiningDate")RequestBody joiningDate,
            @Part("joiningTime")RequestBody joiningTime,
            @Part("profession")RequestBody profession,
            @Part MultipartBody.Part  image);

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> matchData(
            @Field("username") String username,
            @Field("password") String password
    );

}

package com.codesolution.mynewapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterBaseUrl {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://omninos.xyz/gtRental/index.php/api/Demo/";


    static Retrofit getRetrofit() {
        retrofit =new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}

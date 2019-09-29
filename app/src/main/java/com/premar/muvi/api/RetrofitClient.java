package com.premar.muvi.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient(String baseURL){
        if (okHttpClient == null)
            initOkHttp();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp(){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = httpClient.build();
    }
}

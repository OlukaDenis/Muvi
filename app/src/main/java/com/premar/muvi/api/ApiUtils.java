package com.premar.muvi.api;

public class ApiUtils {
    private ApiUtils(){}
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}

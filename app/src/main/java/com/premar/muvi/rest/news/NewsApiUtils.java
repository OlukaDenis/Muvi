package com.premar.muvi.rest.news;
import com.premar.muvi.rest.RetrofitClient;

public class NewsApiUtils {
    private NewsApiUtils(){}
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static NewsApiService getNewsApiService(){
        return RetrofitClient.getClient(BASE_URL).create(NewsApiService.class);
    }
}

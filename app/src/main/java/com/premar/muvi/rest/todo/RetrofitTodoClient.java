package com.premar.muvi.rest.todo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTodoClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://salty-reef-12075.herokuapp.com";

    public static Retrofit getTodoClient() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

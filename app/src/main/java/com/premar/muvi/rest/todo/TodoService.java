package com.premar.muvi.rest.todo;

import com.premar.muvi.model.Todos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TodoService {
    @GET("/todo")
    Call<List<Todos>> getAllTodos();
}

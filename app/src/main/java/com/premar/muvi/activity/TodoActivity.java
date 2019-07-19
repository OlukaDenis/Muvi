package com.premar.muvi.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.adapter.TodoAdapter;
import com.premar.muvi.model.Todos;
import com.premar.muvi.rest.todo.RetrofitTodoClient;
import com.premar.muvi.rest.todo.TodoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {
    ProgressDialog dialog;
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Hang on!");
        dialog.setMessage("Fetching todos...");
        dialog.show();


        /*Create handle for the RetrofitInstance interface*/
        TodoService todoService = RetrofitTodoClient.getTodoClient().create(TodoService.class);
        Call<List<Todos>> call = todoService.getAllTodos();
        call.enqueue(new Callback<List<Todos>>() {
            @Override
            public void onResponse(Call<List<Todos>> call, Response<List<Todos>> response) {
                dialog.dismiss();
                generateTodoList(response.body());
            }

            @Override
            public void onFailure(Call<List<Todos>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(TodoActivity.this, "Something went wrong, Please try again later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void generateTodoList(List<Todos> todosList) {
        recyclerView = findViewById(R.id.todo_recyclerview);
        todoAdapter = new TodoAdapter(todosList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(todoAdapter);

    }
}

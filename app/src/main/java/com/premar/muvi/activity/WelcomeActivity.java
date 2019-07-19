package com.premar.muvi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.premar.muvi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.goMovies) Button go_movies;
    @BindView(R.id.goNews) Button go_news;
    @BindView(R.id.viewTodos) Button view_todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.goMovies)
    void loadMovie(){
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.goNews)
    void loadNews(){
        Intent intent = new Intent(WelcomeActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.viewTodos)
    void loadTodos(){
        Intent intent = new Intent(WelcomeActivity.this, TodoActivity.class);
        startActivity(intent);
    }
}

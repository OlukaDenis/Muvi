package com.premar.muvi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.model.Post;
import com.premar.muvi.rest.news.NewsApiService;
import com.premar.muvi.rest.news.NewsApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    @BindView(R.id.et_title)
    EditText title;

    @BindView(R.id.et_body)
    EditText body;

    @BindView(R.id.submit_post)
    Button btnSubmit;

    @BindView(R.id.tv_response)
    TextView tv_response;

    private NewsApiService newsApiService;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        newsApiService = NewsApiUtils.getNewsApiService();
    }

    @OnClick(R.id.submit_post)
    void SubmitPost(){
        String eTitle = title.getText().toString().trim();
        String eBody = body.getText().toString().trim();
        if(!TextUtils.isEmpty(eTitle) && !TextUtils.isEmpty(eBody)){
            sendPost(eTitle, eBody);
        }

    }

    private void sendPost(String eTitle, String eBody) {
        newsApiService.savePost(eTitle, eBody, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse( Call<Post> call,  Response<Post> response) {
                if(response.isSuccessful()){
                    showResponse(response.body().toString());
                    title.setText("");
                    body.setText("");
                    Log.i(TAG,"News post successfully submitted" );
                    Toast.makeText(getApplicationContext(), "News post successfully submitted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable throwable) {
                Log.i(TAG,"Unable to submit post" );
                Toast.makeText(getApplicationContext(), "Unable to submit post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showResponse(String response) {
        if(tv_response.getVisibility() == View.GONE){
            tv_response.setVisibility(View.VISIBLE);
        }
        tv_response.setText(response);
    }
}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity2 extends AppCompatActivity {
    private final Retrofit retrofit = new RetrofitService().getRetrofit();
    private final Controller controller = retrofit.create(Controller.class);

    private LinearLayout linearLayout;
    private TextView tvComments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        linearLayout = findViewById(R.id.postsList);
        tvComments = findViewById(R.id.comments);
        int extraValue = getIntent().getIntExtra("user", 0);
        Call<List<Post>> call = controller.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> allPosts = response.body();
                    if (allPosts != null){
                        for (Post post : allPosts){
                            int userId = post.getUserId();
                            if (userId == extraValue){
                                TextView tv = new TextView(MainActivity2.this);
                                tv.setText(post.getTitle());
                                tv.setOnClickListener(view -> {
                                    Call<List<Comment>> callCom = controller.getComments(post.getId());
                                    callCom.enqueue(new Callback<List<Comment>>() {
                                        @Override
                                        public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                                            List<Comment> toList = response.body();
                                            if (toList != null){
                                                String commentsText = "";
                                                for (Comment comment : toList){
                                                    String com = comment.getName() + "\n" + comment.getEmail() +
                                                            "\n" + comment.getBody() + "\n------------------";
                                                    commentsText = commentsText + com;
                                                }
                                                tvComments.setText(commentsText);

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Comment>> call, Throwable t) {

                                        }
                                    });
                                });
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(0, 0, 0, 0 );
                                float textSizeInSp = 10; // Example: 16 sp
                                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
                                tv.setLayoutParams(layoutParams);
                                linearLayout.addView(tv);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}
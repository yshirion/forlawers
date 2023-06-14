package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Controller {


    @GET("users")
    Call<List<User>> getUsers();

    @GET("todos")
    Call<List<Todo>> getTodos();

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("comments?postId={id}")
    Call<List<Comment>> getComments(@Path("id") int postId);
}


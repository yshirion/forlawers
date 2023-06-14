package com.example.myapplication;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//Class of retrofit manage the network.
public class RetrofitService {

    private Retrofit retrofit;
    private static RetrofitService instance = null;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(ScalarsConverterFactory
                        .create())
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .setLenient()
                                .create()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitService getInstance() {
        if (instance == null) {
            instance = new RetrofitService();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}


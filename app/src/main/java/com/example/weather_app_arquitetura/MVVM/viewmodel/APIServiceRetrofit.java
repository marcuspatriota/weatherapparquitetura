package com.example.weather_app_arquitetura.MVVM.viewmodel;

import com.rperazzo.weatherapp.data.model.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceRetrofit {
    private static OkHttpClient mClient = new OkHttpClient();

    public static WeatherService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.apiURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();

        return retrofit.create(WeatherService.class);
    }
}



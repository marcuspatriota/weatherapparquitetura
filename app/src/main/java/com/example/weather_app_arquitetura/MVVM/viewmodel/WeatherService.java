package com.example.weather_app_arquitetura.MVVM.viewmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("find")
    Call<FindResult> find(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}


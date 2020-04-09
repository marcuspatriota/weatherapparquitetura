package com.example.weather_app_arquitetura.presenter;


import com.example.weather_app_arquitetura.WeatherManager;

public interface MainActivityIF {
    void onFinishLoadingWithError();
    void onFinishLoading(WeatherManager.FindResult result);
    void searchByName();

}

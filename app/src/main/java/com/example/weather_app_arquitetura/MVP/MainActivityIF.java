package com.example.weather_app_arquitetura.MVP;


public interface MainActivityIF {
    void onFinishLoadingWithError();
    void onFinishLoading(WeatherManager.FindResult result);
    void searchByName();

}

package com.example.weather_app_arquitetura.presenter;

public interface WeatherPresenterIF {
    void searchByName(String search);
    void updateUnitIfNecessary(String unit);

}

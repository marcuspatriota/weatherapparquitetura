package com.example.weather_app_arquitetura.MVP;

public interface WeatherPresenterIF {
    void searchByName(String search);
    void updateUnitIfNecessary(String unit);

}

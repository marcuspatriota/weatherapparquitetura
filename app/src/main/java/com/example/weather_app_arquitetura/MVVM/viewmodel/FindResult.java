package com.example.weather_app_arquitetura.MVVM.viewmodel;

import com.rperazzo.weatherapp.data.model.City;

import java.util.List;

public class FindResult {
    public final List<City> list;

    public FindResult(List<City> list) {
        this.list = list;
    }
}

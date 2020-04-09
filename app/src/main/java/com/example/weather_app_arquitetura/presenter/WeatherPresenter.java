package com.example.weather_app_arquitetura.presenter;

import android.content.Context;

import com.example.weather_app_arquitetura.WeatherManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherPresenter implements WeatherPresenterIF {

    private WeatherSharedPref mSharedPref;
    private MainActivityIF mainActivityIF;
    private Boolean isCity = false;

    public WeatherPresenter(MainActivityIF activityIF){
        mainActivityIF=activityIF;
        mSharedPref = new WeatherSharedPref((Context) mainActivityIF);

    }

    @Override
    public void searchByName( String search) {
        WeatherManager.WeatherService wService = WeatherManager.getService();
        String units = mSharedPref.getTemperatureUnit();
        final Call<WeatherManager.FindResult> findCall = wService.find(search, units, WeatherManager.API_KEY);
        findCall.enqueue(new Callback<WeatherManager.FindResult>() {
            @Override
            public void onResponse(Call<WeatherManager.FindResult> call, Response<WeatherManager.FindResult> response) {
                isCity = true;
                mainActivityIF.onFinishLoading(response.body());
            }
            @Override
            public void onFailure(Call<WeatherManager.FindResult> call, Throwable t) {
                isCity = true;
                mainActivityIF.onFinishLoadingWithError();
            }
        });
    }
    public void updateUnitIfNecessary(String newUnits) {
        String currentUnits = mSharedPref.getTemperatureUnit();
        if (!currentUnits.equals(newUnits)) {
            mSharedPref.setTemperatureUnit(newUnits);
            mainActivityIF.searchByName();
        }
    }
    public Boolean isSearchCity(){
        return isCity;
    }

}

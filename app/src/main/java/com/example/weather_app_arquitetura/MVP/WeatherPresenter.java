package com.example.weather_app_arquitetura.MVP;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherPresenter implements WeatherPresenterIF {

    private WeatherSharedPref mSharedPref;
    private MainActivityIF mainActivityIF;
    private Boolean isSearchCity = false;

    public WeatherPresenter(MainActivityIF activityIF){
        mainActivityIF=activityIF;
        mSharedPref = new WeatherSharedPref((Context) mainActivityIF);

    }

    @Override
    public void searchByName(String search) {
        WeatherManager.WeatherService wService = WeatherManager.getService();
        String units = mSharedPref.getTemperatureUnit();
        final Call<WeatherManager.FindResult> findCall = wService.find(search, units, WeatherManager.API_KEY);
        findCall.enqueue(new Callback<WeatherManager.FindResult>() {
            @Override
            public void onResponse(Call<WeatherManager.FindResult> call, Response<WeatherManager.FindResult> response) {
                isSearchCity = true;
                mainActivityIF.onFinishLoading(response.body());
            }
            @Override
            public void onFailure(Call<WeatherManager.FindResult> call, Throwable t) {
                searchCity(true);
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
        return isSearchCity;
    }
    private void searchCity(Boolean searchCity){
        this.isSearchCity = searchCity;
    }

}

package com.example.weather_app_arquitetura.presenter;

import android.content.Context;
import android.content.SharedPreferences;

public class WeatherSharedPref {

    private static final String PREFERENCE_NAME = "com.example.weather_app_arquiterura.shared";
    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";
    private SharedPreferences mSharedPref;
    private MainActivityIF mainActivityIF;


    public WeatherSharedPref(Context context){
        mSharedPref = context.getSharedPreferences(PREFERENCE_NAME, context.MODE_PRIVATE);
        mainActivityIF = (MainActivityIF) context;
    }


    public void setTemperatureUnit(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }

    public String getTemperatureUnit() {
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }
}

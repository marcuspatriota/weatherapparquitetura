package com.example.weather_app_arquitetura.MVVM.data.model;

import android.content.Context;
import android.content.SharedPreferences;

public class WeatherSharedPref {
    private static final String PREFERENCE_NAME = "com.rperazzo.weatherapp.shared";
    private static final String TEMPERATURE_UNIT_KEY = "TEMPERATURE_UNIT_KEY";
    private SharedPreferences mSharedPref;


    public WeatherSharedPref(Context context){
        mSharedPref = context.getSharedPreferences(PREFERENCE_NAME, context.MODE_PRIVATE);
    }


    public void setTemperatureUnit(String value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(TEMPERATURE_UNIT_KEY, value);
        editor.apply();
    }

    public String getTemperatureUnit() {
        return mSharedPref.getString(TEMPERATURE_UNIT_KEY, "metric");
    }

    public static String getPreferenceName() {
        return PREFERENCE_NAME;
    }

    public static String getTemperatureUnitKey() {
        return TEMPERATURE_UNIT_KEY;
    }
}

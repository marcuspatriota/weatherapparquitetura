package com.example.weather_app_arquitetura.MVP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_app_arquitetura.R;

import java.util.ArrayList;

public class FindItemAdapter extends ArrayAdapter<WeatherManager.City> {
    private WeatherSharedPref  weatherSharedPref;

    public FindItemAdapter(Context context, ArrayList<WeatherManager.City> cities) {
        super(context, 0, cities);
        weatherSharedPref = new WeatherSharedPref(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final WeatherManager.City city = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.city_list_item, parent, false);
        }
        TextView cityName = convertView.findViewById(R.id.cityNameTxt);
        cityName.setText(city.getTitle());

        TextView description = convertView.findViewById(R.id.descriptionTxt);
        description.setText(city.getDescription());

        TextView metric = convertView.findViewById(R.id.metricTxt);
        String units = weatherSharedPref.getTemperatureUnit();
        if ("metric".equals(units)) {
            metric.setText("ºC");
        } else {
            metric.setText("ºF");
        }

        TextView temp = convertView.findViewById(R.id.tempTxt);
        temp.setText(city.getTemperature());

        TextView wind = convertView.findViewById(R.id.windTxt);
        if ("metric".equals(units)) {
            wind.setText(city.getWind() + " m/s");
        } else {
            wind.setText(city.getWind() + " m/h");
        }

        TextView clouds = convertView.findViewById(R.id.cloudsTxt);
        clouds.setText(city.getClouds());

        TextView pressure = convertView.findViewById(R.id.pressureTxt);
        pressure.setText(city.getPressure());

        int resId = getContext().getResources().getIdentifier(
                "w_"+city.weather.get(0).icon,
                "drawable",
                getContext().getPackageName());

        ImageView icon = convertView.findViewById(R.id.weatherIcon);
        icon.setImageResource(resId);

        return convertView;
    }
}
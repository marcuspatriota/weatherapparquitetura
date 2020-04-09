package com.example.weather_app_arquitetura;


import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app_arquitetura.MVP.MainActivityIF;
import com.example.weather_app_arquitetura.MVP.WeatherManager;
import com.example.weather_app_arquitetura.MVP.WeatherPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class WeatherPresenterTest {
    private MainActivityIF mainActivityIF;
    private WeatherPresenter weatherPresenter;
    private Boolean testResult = false;

    @Before
    public void setUp() {
        mainActivityIF = new MainAc();
        weatherPresenter = new WeatherPresenter(mainActivityIF);
    }

    @Test
    public void searchByName(){
        weatherPresenter.searchByName("Sertania");
        Boolean aBoolean = weatherPresenter.isSearchCity();
        Assert.assertTrue(aBoolean);
    }

     class MainAc extends AppCompatActivity implements MainActivityIF {
        @Override
        public void onFinishLoadingWithError() {
            testResult = true;
        }

        @Override
        public void onFinishLoading(WeatherManager.FindResult result) {
            testResult = true;
        }

        @Override
        public void searchByName() {

        }
    }
}

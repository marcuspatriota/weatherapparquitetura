package com.example.weather_app_arquitetura.MVVM.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.rperazzo.weatherapp.data.model.Constants;
import com.rperazzo.weatherapp.data.model.WeatherSharedPref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    private String search;

    private MutableLiveData<String> _userSentSearch = new MutableLiveData<>();
    public LiveData<String> userSentSearch = _userSentSearch;

    private MutableLiveData<FindResult> _cityLD = new MutableLiveData<>() ;
    public LiveData<FindResult> cityLD = _cityLD;

    public MutableLiveData<FindResult> getCityLD(){
        return _cityLD;
    }

    public void set_cityLD (FindResult result){
        _cityLD.setValue(result);
        cityLD = _cityLD;
    }

    public void clickSearchButton(Context context){
         _userSentSearch.setValue(search);
        if(!isDeviceConnected(context)){
             Toast.makeText(context, "No connection!", Toast.LENGTH_LONG).show();
             return;
         }

        if (TextUtils.isEmpty(search)) {
            return;
        }
        getCities(context);
    }

    private void getCities(Context context){
        WeatherService wService = APIServiceRetrofit.getService();
        final Call<FindResult> findCall = wService.find(search, getTempUnitVM(context), Constants.apiKey);
        findCall.enqueue(new Callback<FindResult>() {
            @Override
            public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                set_cityLD(response.body());
            }

            @Override
            public void onFailure(Call<FindResult> call, Throwable t) {
                Log.d("Renato","API NAO RESPONDEU");
            }
        });
    }

    public boolean isDeviceConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void setUserSearch(String search){
        this.search = search;
    }

    public void updateUnitIfNecessary(String s,Context context ){
        if(!(getTempUnitVM(context).equals(s))) setTempUnitVm(s,context);
        getCities(context);
    }

    public String getTempUnitVM(Context context){
        WeatherSharedPref wsp = new WeatherSharedPref(context);
        return wsp.getTemperatureUnit();
    }

    public void setTempUnitVm(String s,Context context){
        WeatherSharedPref wsp = new WeatherSharedPref(context);
        wsp.setTemperatureUnit(s);
    }
}



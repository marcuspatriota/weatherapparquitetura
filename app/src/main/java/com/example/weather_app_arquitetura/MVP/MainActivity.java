package com.example.weather_app_arquitetura.MVP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_app_arquitetura.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainActivityIF {

    private WeatherPresenterIF weatherPresenter;

    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    private FindItemAdapter mAdapter;
    private ArrayList<WeatherManager.City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mList = (ListView) findViewById(R.id.list);

        mAdapter = new FindItemAdapter(this, cities);
        mList.setAdapter(mAdapter);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchByName();
                }
                return false;
            }
        });
        intWeather();
    }
    private void intWeather(){
        weatherPresenter = new WeatherPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_celcius) {
            weatherPresenter.updateUnitIfNecessary("metric");
            return true;
        } else if (id == R.id.menu_fahrenheit) {
            weatherPresenter.updateUnitIfNecessary("imperial");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSearchClick(View view) {
        searchByName();
    }

    private void onStartLoading() {
        mList.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                     getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void onFinishLoading(WeatherManager.FindResult result){

        mProgressBar.setVisibility(View.GONE);
        cities.clear();

        if (result.list.size() > 0) {
            cities.addAll(result.list);
            mList.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mTextView.setText("No results.");
        }
    }

    @Override
    public void onFinishLoadingWithError() {
        mProgressBar.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mTextView.setText("Error");
    }

    private boolean isDeviceConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void searchByName() {
        if (!isDeviceConnected()) {
            Toast.makeText(this, "No connection!", Toast.LENGTH_LONG).show();
            return;
        }
        String search = mEditText.getText().toString();
        if (TextUtils.isEmpty(search)) {
            return;
        }
        onStartLoading();
        weatherPresenter.searchByName(search);
    }


}

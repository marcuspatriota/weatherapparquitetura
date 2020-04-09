package com.example.weather_app_arquitetura.MVVM.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rperazzo.weatherapp.R;
import com.rperazzo.weatherapp.viewmodel.FindResult;
import com.rperazzo.weatherapp.data.model.City;
import com.rperazzo.weatherapp.viewmodel.WeatherViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FindItemAdapter findItemAdapter;
    private EditText mEditText;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private ListView mList;
    WeatherViewModel viewModel;
    private ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewModel();
        changeInputCityField();
        setupObservers();
    }

    private void setupObservers() {
        viewModel.userSentSearch.observe(this, new Observer<String>(){
            @Override
            public void onChanged(@Nullable String s) {
                viewModel.setUserSearch(mEditText.getText().toString());
            }
        });

        viewModel.getCityLD().observe(this, new Observer<FindResult>(){

            @Override
            public void onChanged(@Nullable FindResult findResult) {
                onFinishLoading(findResult);}
        });
    }

    private void changeInputCityField(){
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    viewModel.setUserSearch(mEditText.getText().toString());
                }
                return false;
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
    }

    private void initViews(){
        mEditText =  findViewById(R.id.editText);
        mTextView =  findViewById(R.id.textView);
        mProgressBar =  findViewById(R.id.progressBar);
        mList = findViewById(R.id.list);
        findItemAdapter = new FindItemAdapter(this, cities);
        mList.setAdapter(findItemAdapter);
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
            viewModel.updateUnitIfNecessary("metric",this);
            return true;
        } else if (id == R.id.menu_fahrenheit) {
            viewModel.updateUnitIfNecessary("imperial",this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSearchClick(View view) {
        onStartLoading();
        viewModel.clickSearchButton(this);
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

    private void onFinishLoading(FindResult result){

        mProgressBar.setVisibility(View.GONE);
        cities.clear();

        if (result.list.size() > 0) {
            cities.addAll(result.list);
            mList.setVisibility(View.VISIBLE);
            findItemAdapter.notifyDataSetChanged();
        } else {
            mTextView.setText("No results.");
        }
    }

}

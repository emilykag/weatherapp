package com.emilykag.weatherapp.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.adapters.ViewPagerAdapter;
import com.emilykag.weatherapp.fragments.ForecastFragment;
import com.emilykag.weatherapp.fragments.MainForecastFragment;
import com.emilykag.weatherapp.fragments.WeeklyForecastFragment;
import com.emilykag.weatherapp.models.Forecast;

public class ForecastActivity extends AppCompatActivity implements MainForecastFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
    }

    @Override
    public void setWeeklyForecastList(List<Forecast> listForecast) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
        WeeklyForecastFragment weeklyForecastFragment = (WeeklyForecastFragment) adapter.getItem(1);

        if (weeklyForecastFragment != null) {
            weeklyForecastFragment.setWeeklyForecastList(listForecast);
        }
    }

    @Override
    public void setWeatherImage(String code) {
        ForecastFragment forecastFragment = (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (forecastFragment != null) {
            forecastFragment.setWeatherImage(code);
        }
    }
}

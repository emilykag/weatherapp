package mobile.weatherapp.emilykag.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.adapters.ViewPagerAdapter;
import mobile.weatherapp.emilykag.weatherapp.fragments.ForecastFragment;
import mobile.weatherapp.emilykag.weatherapp.fragments.MainForecastFragment;
import mobile.weatherapp.emilykag.weatherapp.fragments.WeeklyForecastFragment;
import mobile.weatherapp.emilykag.weatherapp.models.Forecast;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

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

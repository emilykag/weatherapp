package com.emilykag.weatherapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.fragments.MainForecastFragment;
import com.emilykag.weatherapp.utils.ScheduleRefresh;

public class SettingsActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSettings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addPreferencesFromResource(R.xml.general_prefs);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {
            MainForecastFragment.getInstance().updateWeather(1);
        } else if (key.equals(getString(R.string.pref_refresh_key))) {
            int interval = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_refresh_key),
                    getString(R.string.pref_refresh_default)));
            if (interval == 0) {
                ScheduleRefresh.stopScheduledRefresh(this);
            } else {
                ScheduleRefresh.scheduleRefresh(this, interval);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

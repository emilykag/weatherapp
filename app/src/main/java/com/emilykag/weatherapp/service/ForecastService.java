package com.emilykag.weatherapp.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.interfaces.Callback;
import com.emilykag.weatherapp.models.Response;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.utils.DateUtils;
import com.emilykag.weatherapp.utils.JSONParser;
import com.emilykag.weatherapp.utils.UrlConnection;

import java.util.Calendar;

public class ForecastService extends IntentService {

    public ForecastService() {
        super("ForecastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String location = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                    .getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            int type = intent.getIntExtra("type", 1);

            Response response = UrlConnection.getHttpResponse(location, "c");
            publishResults(response, type);
        }
    }

    private void publishResults(Response response, int type) {
        Intent intent = new Intent("ForecastService");
        intent.putExtra("response", response);
        intent.putExtra("type", type);
        sendBroadcast(intent);
    }
}

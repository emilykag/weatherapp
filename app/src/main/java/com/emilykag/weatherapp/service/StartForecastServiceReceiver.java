package com.emilykag.weatherapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.emilykag.weatherapp.utils.Tools;

public class StartForecastServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent forecastIntent = new Intent(context, ForecastService.class);
        if (Tools.isNetworkAvailable(context)) {
            context.startService(forecastIntent);
        }
    }
}

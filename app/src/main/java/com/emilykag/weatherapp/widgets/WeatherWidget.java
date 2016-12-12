package com.emilykag.weatherapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.emilykag.weatherapp.service.WeatherWidgetService;
import com.emilykag.weatherapp.service.ClockWidgetService;

public class WeatherWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        context.startService(new Intent(context, ClockWidgetService.class));
        context.startService(new Intent(context, WeatherWidgetService.class));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context, ClockWidgetService.class));
        context.startService(new Intent(context, WeatherWidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context, ClockWidgetService.class));
        context.stopService(new Intent(context, WeatherWidgetService.class));
    }
}


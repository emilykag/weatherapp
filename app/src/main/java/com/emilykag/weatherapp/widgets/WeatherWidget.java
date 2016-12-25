package com.emilykag.weatherapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.activities.ForecastActivity;
import com.emilykag.weatherapp.service.ForecastService;
import com.emilykag.weatherapp.service.WeatherWidgetService;
import com.emilykag.weatherapp.service.ClockWidgetService;
import com.emilykag.weatherapp.utils.Tools;
import com.emilykag.weatherapp.utils.WeatherImageTool;

import static com.emilykag.weatherapp.service.WeatherWidgetService.WIDGET_BUTTON;
import static com.emilykag.weatherapp.service.WeatherWidgetService.WIDGET_ON_CLICK;
import static com.emilykag.weatherapp.service.ForecastService.WIDGET_LOCATION_NOT_FOUND;
import static com.emilykag.weatherapp.service.ForecastService.WIDGET_ERROR_UPDATE;

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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            Intent forecastIntent = new Intent(context, ForecastService.class);
            forecastIntent.putExtra("type", 3);
            context.startService(forecastIntent);
            Tools.setProgressBarVisibility(context, View.VISIBLE, View.GONE);
        } else if (WIDGET_LOCATION_NOT_FOUND.equals(intent.getAction())) {
            Toast.makeText(context, context.getString(R.string.location_not_found), Toast.LENGTH_SHORT).show();
        } else if (WIDGET_ERROR_UPDATE.equals(intent.getAction())) {
            int errorCode = intent.getIntExtra("errorCode", 0);
            Tools.showFailure(context, errorCode);
        } else if (WIDGET_ON_CLICK.equals(intent.getAction())) {
            Intent mainIntent = new Intent(context, ForecastActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(mainIntent);
        }
    }
}


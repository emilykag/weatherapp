package com.emilykag.weatherapp.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.activities.ForecastActivity;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.utils.WeatherImageTool;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseActions;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseUtils;
import com.emilykag.weatherapp.widgets.WeatherWidget;

public class WeatherWidgetService extends Service {

    public static final String WIDGET_BUTTON = "com.emilykag.weatherapp.service.WeatherWidgetService.WIDGET_BUTTON";
    public static final String WIDGET_ON_CLICK = "com.emilykag.weatherapp.service.WeatherWidgetService.WIDGET_ON_CLICK";

    private static IntentFilter intentFilter;

    static {
        intentFilter = new IntentFilter();
    }

    public WeatherWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(weatherReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(weatherReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        return super.onStartCommand(intent, flags, startId);
    }

    private final BroadcastReceiver weatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateWeather();
        }
    };

    private void updateWeather() {
        WeatherValues weatherValues = DatabaseUtils.retrieveWeatherValues(getApplicationContext());
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.weather_widget);
        if (weatherValues != null) {
            views.setTextViewText(R.id.textViewLocation, weatherValues.getCity());
            views.setTextViewText(R.id.textViewNowTemp, weatherValues.getTemperature() + (char) 0x00B0);
            views.setTextViewText(R.id.textViewMinTemp, weatherValues.getListForecast().get(0).getLow() + (char) 0x00B0);
            views.setTextViewText(R.id.textViewMaxTemp, weatherValues.getListForecast().get(0).getHigh() + (char) 0x00B0);
            views.setTextViewText(R.id.textViewLastUpdated, getString(R.string.last_updated, weatherValues.getLastUpdated()));
            WeatherImageTool weatherImageTool = new WeatherImageTool(this, views);
            weatherImageTool.setWeatherImage(weatherValues.getCode(), "widget");
            weatherImageTool.setWeatherImage(weatherValues.getCode(), "widgetBack");
            views.setOnClickPendingIntent(R.id.imageButtonUpdateWidget, getPendingIntent());
            views.setOnClickPendingIntent(R.id.linearLayoutWidgetBackground, getOpenAppPendingIntent());
        }
        ComponentName widget = new ComponentName(this, WeatherWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, views);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, WeatherWidget.class);
        intent.setAction(WIDGET_BUTTON);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private PendingIntent getOpenAppPendingIntent() {
        Intent intent = new Intent(this, WeatherWidget.class);
        intent.setAction(WIDGET_ON_CLICK);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }
}

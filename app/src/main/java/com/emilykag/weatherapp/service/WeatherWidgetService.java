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

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.utils.WeatherImageTool;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseActions;
import com.emilykag.weatherapp.widgets.WeatherWidget;

public class WeatherWidgetService extends Service {

    public static String UPDATE_WIDGET_ACTION = "YourAwesomeAction";

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
        DatabaseActions databaseActions = new DatabaseActions(this);
        WeatherValues weatherValues = databaseActions.retrieveWeatherValues();
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.weather_widget);
        if (weatherValues != null) {
            views.setTextViewText(R.id.textViewLocation, weatherValues.getCity());
            views.setTextViewText(R.id.textViewNowTemp, weatherValues.getTemperature() + (char) 0x00B0);
            views.setTextViewText(R.id.textViewMinTemp, weatherValues.getListForecast().get(0).getLow() + (char) 0x00B0);
            views.setTextViewText(R.id.textViewMaxTemp, weatherValues.getListForecast().get(0).getHigh() + (char) 0x00B0);
            views.setImageViewResource(R.id.imageButtonUpdateWidget, android.R.drawable.ic_popup_sync);
            views.setTextViewText(R.id.textViewLastUpdated, getString(R.string.last_updated, weatherValues.getLastUpdated()));
            WeatherImageTool weatherImageTool = new WeatherImageTool(this, views);
            weatherImageTool.setWeatherImage(weatherValues.getCode(), "widget");
            weatherImageTool.setWeatherImage(weatherValues.getCode(), "widgetBack");
            //views.setOnClickPendingIntent(R.id.imageButtonUpdateWidget, getPendingIntent());
        }
        ComponentName widget = new ComponentName(this, WeatherWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, views);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, WeatherWidget.class);
        intent.setAction(UPDATE_WIDGET_ACTION);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }
}

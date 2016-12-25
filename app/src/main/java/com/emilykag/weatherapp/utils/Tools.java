package com.emilykag.weatherapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.widgets.WeatherWidget;

public class Tools {

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null;
    }

    public static void showFailure(Context context, int statusCode) {
        String message = "Error " + String.valueOf(statusCode);
        if (!Tools.isNetworkAvailable(context)) {
            message = context.getString(R.string.no_internet_message);
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setProgressBarVisibility(Context context, int progressVarVisibility, int refreshButtonVisibility) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        views.setViewVisibility(R.id.progressBarWidget, progressVarVisibility);
        views.setViewVisibility(R.id.imageButtonUpdateWidget, refreshButtonVisibility);
        ComponentName widget = new ComponentName(context, WeatherWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(widget, views);
    }
}

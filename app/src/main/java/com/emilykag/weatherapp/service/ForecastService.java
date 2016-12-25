package com.emilykag.weatherapp.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.interfaces.Callback;
import com.emilykag.weatherapp.models.Response;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.utils.DateUtils;
import com.emilykag.weatherapp.utils.JSONParser;
import com.emilykag.weatherapp.utils.Tools;
import com.emilykag.weatherapp.utils.UrlConnection;
import com.emilykag.weatherapp.utils.WeatherImageTool;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseActions;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseUtils;
import com.emilykag.weatherapp.widgets.WeatherWidget;

public class ForecastService extends IntentService {

    public static final String WIDGET_LOCATION_NOT_FOUND = "com.emilykag.weatherapp.service.ForecastService.WIDGET_LOCATION_NOT_FOUND";
    public static final String WIDGET_ERROR_UPDATE = "com.emilykag.weatherapp.service.ForecastService.WIDGET_ERROR_UPDATE";

    public ForecastService() {
        super("ForecastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String location = PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            int type = intent.getIntExtra("type", 1);

            Response response = UrlConnection.getHttpResponse(location, "c");
            publishResults(response, type);
        }
    }

    private void publishResults(Response response, int type) {
        if (type == 3) {
            updateWidget(response);
        } else {
            Intent intent = new Intent("ForecastService");
            intent.putExtra("response", response);
            intent.putExtra("type", type);
            sendBroadcast(intent);
        }
    }

    private void updateWidget(Response response) {
        WeatherValues weatherValues;
        if (response.getCode() == 200) {
            weatherValues = JSONParser.jsonParser(response.getResponse());

            if (weatherValues != null) {
                DatabaseUtils.updateWeatherValues(this, weatherValues);
                startService(new Intent(this, WeatherWidgetService.class));
            } else {
                sendWidgetAction(WIDGET_LOCATION_NOT_FOUND, -1);
            }
        } else {
            sendWidgetAction(WIDGET_ERROR_UPDATE, response.getCode());
        }
        Tools.setProgressBarVisibility(this, View.GONE, View.VISIBLE);
    }

    private void sendWidgetAction(String action, int extra) {
        try {
            Intent intent = new Intent(this, WeatherWidget.class);
            intent.setAction(action);
            if (extra == -1) {
                intent.putExtra("errorCode", extra);
            }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}

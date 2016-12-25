package com.emilykag.weatherapp.utils.databaseutils;

import android.content.Context;

import com.emilykag.weatherapp.models.WeatherValues;

public class DatabaseUtils {

    public static void updateWeatherValues(Context context, WeatherValues weatherValues) {
        DatabaseActions databaseActions = new DatabaseActions(context);
        WeatherValues oldWeatherValues = databaseActions.retrieveWeatherValues();
        databaseActions.updateWeatherValues(oldWeatherValues, weatherValues);
    }

    public static void addWeatherValues(Context context, WeatherValues weatherValues) {
        DatabaseActions databaseActions = new DatabaseActions(context);
        databaseActions.addWeatherValues(weatherValues);
    }

    public static WeatherValues retrieveWeatherValues(Context context) {
        DatabaseActions databaseActions = new DatabaseActions(context);
        return databaseActions.retrieveWeatherValues();
    }

}

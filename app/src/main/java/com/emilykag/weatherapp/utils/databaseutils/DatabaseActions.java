package com.emilykag.weatherapp.utils.databaseutils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.emilykag.weatherapp.models.Forecast;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatabaseActions {

    private Context context;

    DatabaseActions(Context context) {
        this.context = context;
    }

    WeatherValues retrieveWeatherValues() {
        WeatherValues weatherValues = null;
        Cursor cursor = context.getContentResolver().query(
                WeatherContract.WeatherEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry._ID));
                String city = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_CITY));
                String windspeed = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_WINDSPEED));
                String humidity = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_HUMIDITY));
                String visibility = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_VISIBILITY));
                String sunrise = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_SUNRISE));
                String sunset = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_SUNSET));
                String code = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_CODE));
                String temp = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_TEMPERATURE));
                String description = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_NOW_DESCRIPTION));
                String lastUpdated = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_LAST_UPDATED));

                weatherValues = new WeatherValues(id, city, windspeed, humidity, visibility, sunrise, sunset, code,
                        temp, description, lastUpdated, retrieveForecast());
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return weatherValues;
    }

    private List<Forecast> retrieveForecast() {
        List<Forecast> forecastList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                WeatherContract.ForecastEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry._ID));
                String code = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_CODE));
                String date = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_DATE));
                String day = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_DAY));
                String high = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_HIGH));
                String low = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_LOW));
                String text = cursor.getString(cursor.getColumnIndex(WeatherContract.ForecastEntry.F_TEXT));

                forecastList.add(new Forecast(id, code, date, day, high, low, text));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return forecastList;
    }

    void addWeatherValues(WeatherValues weatherValues) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.WeatherEntry.WV_CITY, weatherValues.getCity());
        values.put(WeatherContract.WeatherEntry.WV_WINDSPEED, weatherValues.getWindSpeed());
        values.put(WeatherContract.WeatherEntry.WV_HUMIDITY, weatherValues.getHumidity());
        values.put(WeatherContract.WeatherEntry.WV_VISIBILITY, weatherValues.getVisibility());
        values.put(WeatherContract.WeatherEntry.WV_SUNRISE, weatherValues.getSunrise());
        values.put(WeatherContract.WeatherEntry.WV_SUNSET, weatherValues.getSunset());
        values.put(WeatherContract.WeatherEntry.WV_CODE, weatherValues.getCode());
        values.put(WeatherContract.WeatherEntry.WV_TEMPERATURE, weatherValues.getTemperature());
        values.put(WeatherContract.WeatherEntry.WV_NOW_DESCRIPTION, weatherValues.getNowDescription());
        values.put(WeatherContract.WeatherEntry.WV_LAST_UPDATED,
                DateUtils.formatLastUpdatedDate(Calendar.getInstance().getTime()));

        Uri movieInsertUri = context.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(movieInsertUri);

        if (movieRowId > 0) {
            Cursor cursor = context.getContentResolver().query(
                    WeatherContract.WeatherEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.close();
            }
        }

        // add forecast
        addForecastValues(weatherValues);
    }

    private void addForecastValues(WeatherValues weatherValues) {
        ContentValues[] contentValues = new ContentValues[weatherValues.getListForecast().size()];
        for (int i = 0; i < contentValues.length; i++) {
            Forecast forecast = weatherValues.getListForecast().get(i);
            contentValues[i] = new ContentValues();
            contentValues[i].put(WeatherContract.ForecastEntry.F_CODE, forecast.getCode());
            contentValues[i].put(WeatherContract.ForecastEntry.F_DATE, forecast.getDate());
            contentValues[i].put(WeatherContract.ForecastEntry.F_DAY, forecast.getDay());
            contentValues[i].put(WeatherContract.ForecastEntry.F_HIGH, forecast.getHigh());
            contentValues[i].put(WeatherContract.ForecastEntry.F_LOW, forecast.getLow());
            contentValues[i].put(WeatherContract.ForecastEntry.F_TEXT, forecast.getText());
        }

        context.getContentResolver().bulkInsert(WeatherContract.ForecastEntry.CONTENT_URI, contentValues);
    }

    public void updateWeatherValues(WeatherValues oldWeatherValues, WeatherValues weatherValues) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.WeatherEntry._ID, oldWeatherValues.get_id());
        values.put(WeatherContract.WeatherEntry.WV_CITY, weatherValues.getCity());
        values.put(WeatherContract.WeatherEntry.WV_WINDSPEED, weatherValues.getWindSpeed());
        values.put(WeatherContract.WeatherEntry.WV_HUMIDITY, weatherValues.getHumidity());
        values.put(WeatherContract.WeatherEntry.WV_VISIBILITY, weatherValues.getVisibility());
        values.put(WeatherContract.WeatherEntry.WV_SUNRISE, weatherValues.getSunrise());
        values.put(WeatherContract.WeatherEntry.WV_SUNSET, weatherValues.getSunset());
        values.put(WeatherContract.WeatherEntry.WV_CODE, weatherValues.getCode());
        values.put(WeatherContract.WeatherEntry.WV_TEMPERATURE, weatherValues.getTemperature());
        values.put(WeatherContract.WeatherEntry.WV_NOW_DESCRIPTION, weatherValues.getNowDescription());
        values.put(WeatherContract.WeatherEntry.WV_LAST_UPDATED,
                DateUtils.formatLastUpdatedDate(Calendar.getInstance().getTime()));

        context.getContentResolver().update(WeatherContract.WeatherEntry.CONTENT_URI,
                values,
                WeatherContract.WeatherEntry._ID + " = ?",
                new String[]{String.valueOf(oldWeatherValues.get_id())}
        );

        for (int i = 0; i < oldWeatherValues.getListForecast().size(); i++) {
            Forecast oldForecast = oldWeatherValues.getListForecast().get(i);
            Forecast forecast = weatherValues.getListForecast().get(i);
            updateForecast(oldForecast, forecast);
        }
    }

    private void updateForecast(Forecast oldForecast, Forecast forecast) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.ForecastEntry._ID, oldForecast.get_id());
        values.put(WeatherContract.ForecastEntry.F_CODE, forecast.getCode());
        values.put(WeatherContract.ForecastEntry.F_DATE, forecast.getDate());
        values.put(WeatherContract.ForecastEntry.F_DAY, forecast.getDay());
        values.put(WeatherContract.ForecastEntry.F_HIGH, forecast.getHigh());
        values.put(WeatherContract.ForecastEntry.F_LOW, forecast.getLow());
        values.put(WeatherContract.ForecastEntry.F_TEXT, forecast.getText());

        context.getContentResolver().update(WeatherContract.ForecastEntry.CONTENT_URI,
                values,
                WeatherContract.ForecastEntry._ID + " = ?",
                new String[]{String.valueOf(oldForecast.get_id())}
        );
    }

    private boolean validateCursor(Cursor valueCursor, ContentValues expectedValues) {
        boolean flag = true;
        if (valueCursor.moveToFirst()) {

            Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

            for (Map.Entry<String, Object> entry : valueSet) {
                String columnName = entry.getKey();
                int idx = valueCursor.getColumnIndex(columnName);
                if (idx != -1) {
                    switch (valueCursor.getType(idx)) {
                        case Cursor.FIELD_TYPE_FLOAT:
                            if (!entry.getValue().equals(valueCursor.getDouble(idx))) {
                                System.out.println(entry.getKey() + " " + entry.getValue() + " - " + valueCursor.getDouble(idx));
                                flag = false;
                            }
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            if (Integer.parseInt(entry.getValue().toString()) != valueCursor.getInt(idx)) {
                                flag = false;
                                System.out.println(entry.getKey() + " " + entry.getValue() + " - " + valueCursor.getInt(idx));
                            }
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            if (!entry.getValue().equals(valueCursor.getString(idx))) {
                                flag = false;
                                System.out.println(entry.getKey() + " " + entry.getValue() + " - " + valueCursor.getString(idx));
                            }
                            break;
                        default:
                            if (!entry.getValue().toString().equals(valueCursor.getString(idx))) {
                                flag = false;
                                System.out.println(entry.getKey() + " " + entry.getValue() + " - " + valueCursor.getString(idx));
                            }
                            break;
                    }
                }
            }
        }
        return flag;
    }

}

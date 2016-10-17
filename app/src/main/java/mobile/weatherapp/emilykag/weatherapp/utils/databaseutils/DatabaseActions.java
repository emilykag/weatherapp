package mobile.weatherapp.emilykag.weatherapp.utils.databaseutils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import mobile.weatherapp.emilykag.weatherapp.models.Forecast;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

public class DatabaseActions {

    private Context context;

    public DatabaseActions(Context context) {
        this.context = context;
    }

    public WeatherValues retrieveWeatherValues() {
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
                String city = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_CITY));
                String windspeed = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_WINDSPEED));
                String humidity = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_HUMIDITY));
                String visibility = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_VISIBILITY));
                String sunrise = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_SUNRISE));
                String sunset = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_SUNSET));
                String code = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_CODE));
                String temp = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_TEMPERATURE));
                String description = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.WV_NOW_DESCRIPTION));

                weatherValues = new WeatherValues(1, city, windspeed, humidity, visibility, sunrise, sunset, code,
                        temp, description, new ArrayList<Forecast>());
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return weatherValues;
    }

    public void addWeatherValues(WeatherValues weatherValues) {
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
            if (!validateCursor(cursor, values)) {
                Toast.makeText(context, "An error occurred. Try again later.", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void addForecastValues(Forecast forecast) {
        ContentValues values = new ContentValues();
        values.put(WeatherContract.ForecastEntry.F_CODE, forecast.getCode());
        values.put(WeatherContract.ForecastEntry.F_DATE, forecast.getDate());
        values.put(WeatherContract.ForecastEntry.F_DAY, forecast.getDay());
        values.put(WeatherContract.ForecastEntry.F_HIGH, forecast.getHigh());
        values.put(WeatherContract.ForecastEntry.F_LOW, forecast.getLow());
        values.put(WeatherContract.ForecastEntry.F_TEXT, forecast.getText());

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
            if (!validateCursor(cursor, values)) {
                Toast.makeText(context, "An error occurred. Try again later.", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
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
                            if (!entry.getValue().equals(valueCursor.getDouble(idx)))
                                flag = false;
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            if (Integer.parseInt(entry.getValue().toString()) != valueCursor.getInt(idx))
                                flag = false;
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            if (!entry.getValue().equals(valueCursor.getString(idx)))
                                flag = false;
                            break;
                        default:
                            if (!entry.getValue().toString().equals(valueCursor.getString(idx)))
                                flag = false;
                            break;
                    }
                }
            }
        }
        return flag;
    }

}

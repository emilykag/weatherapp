package mobile.weatherapp.emilykag.weatherapp.utils.databaseutils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract {

    static final String CONTENT_AUTHORITY = "mobile.weatherapp.emilykag.weatherapp";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_WEATHER = "weather";
    static final String PATH_FORECAST = "forecast";


    static final class WeatherEntry implements BaseColumns {

        // Content URI represents the base location for the table
        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_WEATHER;
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_WEATHER;

        // Define the table schema
        static final String TABLE_NAME_WEATHER_VALUES = "weathervalues";
        static final String WV_CITY = "city";
        static final String WV_WINDSPEED = "windspeed";
        static final String WV_HUMIDITY = "humidity";
        static final String WV_VISIBILITY = "visibility";
        static final String WV_SUNRISE = "sunrise";
        static final String WV_SUNSET = "sunset";
        static final String WV_CODE = "code";
        static final String WV_TEMPERATURE = "temperature";
        static final String WV_NOW_DESCRIPTION = "now_description";

        // Define a function to build a URI to find a specific movie by it's identifier
        static Uri buildWeatherValuesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    static final class ForecastEntry implements BaseColumns {

        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FORECAST).build();

        static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_FORECAST;
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_FORECAST;

        static final String TABLE_NAME_FORECAST = "forecast";
        static final String F_CODE = "code";
        static final String F_DATE = "date";
        static final String F_DAY = "day";
        static final String F_HIGH = "high";
        static final String F_LOW = "low";
        static final String F_TEXT = "text";

        static Uri buildForecastUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

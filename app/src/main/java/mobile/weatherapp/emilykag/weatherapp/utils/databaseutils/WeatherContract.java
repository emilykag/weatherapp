package mobile.weatherapp.emilykag.weatherapp.utils.databaseutils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract {

    public final static String CONTENT_AUTHORITY = "mobile.weatherapp.emilykag.weatherapp";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";
    public static final String PATH_FORECAST = "forecast";


    public static final class WeatherEntry implements BaseColumns {

        // Content URI represents the base location for the table

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_WEATHER;

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_WEATHER;


        // Define the table schema
        public static final String TABLE_NAME_WEATHER_VALUES = "weathervalues";
        public static final String WV_CITY = "city";
        public static final String WV_WINDSPEED = "windspeed";
        public static final String WV_HUMIDITY = "humidity";
        public static final String WV_VISIBILITY = "visibility";
        public static final String WV_SUNRISE = "sunrise";
        public static final String WV_SUNSET = "sunset";
        public static final String WV_CODE = "code";
        public static final String WV_TEMPERATURE = "temperature";
        public static final String WV_NOW_DESCRIPTION = "now_description";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildWeatherValuesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ForecastEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FORECAST).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_FORECAST;

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_FORECAST;

        public static final String TABLE_NAME_FORECAST = "forecast";
        public static final String F_CODE = "code";
        public static final String F_DATE = "date";
        public static final String F_DAY = "day";
        public static final String F_HIGH = "high";
        public static final String F_LOW = "low";
        public static final String F_TEXT = "text";

        public static Uri buildForecastUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

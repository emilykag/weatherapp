package mobile.weatherapp.emilykag.weatherapp.utils.databaseutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqlDB;
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    public WeatherDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_WEATHER_VALUES = "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES +
            " (" + WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY, " +
            WeatherContract.WeatherEntry.WV_CITY + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_WINDSPEED + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_HUMIDITY + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_VISIBILITY + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_SUNRISE + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_SUNSET + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_CODE + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_TEMPERATURE + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.WV_NOW_DESCRIPTION + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_FORECAST = "CREATE TABLE " + WeatherContract.ForecastEntry.TABLE_NAME_FORECAST +
            " (" + WeatherContract.ForecastEntry._ID + " INTEGER PRIMARY KEY, " +
            WeatherContract.ForecastEntry.F_CODE + " TEXT NOT NULL, " +
            WeatherContract.ForecastEntry.F_DATE + " TEXT NOT NULL, " +
            WeatherContract.ForecastEntry.F_DAY + " TEXT NOT NULL, " +
            WeatherContract.ForecastEntry.F_HIGH + " TEXT NOT NULL, " +
            WeatherContract.ForecastEntry.F_LOW + " TEXT NOT NULL, " +
            WeatherContract.ForecastEntry.F_TEXT + " TEXT NOT NULL" +
            ");";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_WEATHER_VALUES);
        sqLiteDatabase.execSQL(CREATE_TABLE_FORECAST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES;
        String query2 = "DROP TABLE IF EXISTS " + WeatherContract.ForecastEntry.TABLE_NAME_FORECAST;
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        this.onCreate(sqLiteDatabase);
    }
}

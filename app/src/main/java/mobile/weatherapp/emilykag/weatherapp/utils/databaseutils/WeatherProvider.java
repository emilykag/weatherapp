package mobile.weatherapp.emilykag.weatherapp.utils.databaseutils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class WeatherProvider extends ContentProvider {

    // Use an int for each URI we will run, this represents the different queries
    private static final int WEATHER_VALUES = 100;
    private static final int WEATHER_VALUES_ID = 101;
    private static final int FORECAST = 200;
    private static final int FORECAST_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private WeatherDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new WeatherDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;
        long _id;
        switch (sUriMatcher.match(uri)) {
            case WEATHER_VALUES:
                cursor = db.query(WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case WEATHER_VALUES_ID:
                _id = ContentUris.parseId(uri);
                cursor = db.query(WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES, projection,
                        WeatherContract.WeatherEntry._ID + " = ?", new String[]{String.valueOf(_id)}, null,
                        null, sortOrder);
                break;
            case FORECAST:
                cursor = db.query(WeatherContract.ForecastEntry.TABLE_NAME_FORECAST, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case FORECAST_ID:
                _id = ContentUris.parseId(uri);
                cursor = db.query(WeatherContract.ForecastEntry.TABLE_NAME_FORECAST, projection,
                        WeatherContract.ForecastEntry._ID + " = ?", new String[]{String.valueOf(_id)}, null,
                        null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case WEATHER_VALUES:
                return WeatherContract.WeatherEntry.CONTENT_TYPE;
            case WEATHER_VALUES_ID:
                return WeatherContract.WeatherEntry.CONTENT_ITEM_TYPE;
            case FORECAST:
                return WeatherContract.ForecastEntry.CONTENT_TYPE;
            case FORECAST_ID:
                return WeatherContract.ForecastEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case WEATHER_VALUES:
                _id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES, null, contentValues);
                if(_id > 0){
                    returnUri =  WeatherContract.WeatherEntry.buildWeatherValuesUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case FORECAST:
                _id = db.insert(WeatherContract.ForecastEntry.TABLE_NAME_FORECAST, null, contentValues);
                if(_id > 0){
                    returnUri = WeatherContract.ForecastEntry.buildForecastUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has changed.
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows; // Number of rows effected

        switch(sUriMatcher.match(uri)){
            case WEATHER_VALUES:
                rows = db.delete(WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES, selection, selectionArgs);
                break;
            case FORECAST:
                rows = db.delete(WeatherContract.ForecastEntry.TABLE_NAME_FORECAST, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because null could delete all rows:
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
            case WEATHER_VALUES:
                rows = db.update(WeatherContract.WeatherEntry.TABLE_NAME_WEATHER_VALUES, contentValues, selection, selectionArgs);
                break;
            case FORECAST:
                rows = db.update(WeatherContract.ForecastEntry.TABLE_NAME_FORECAST, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    public static UriMatcher buildUriMatcher() {
        String content = WeatherContract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, WeatherContract.PATH_WEATHER, WEATHER_VALUES);
        matcher.addURI(content, WeatherContract.PATH_WEATHER + "/#", WEATHER_VALUES_ID);
        matcher.addURI(content, WeatherContract.PATH_FORECAST, FORECAST);
        matcher.addURI(content, WeatherContract.PATH_FORECAST + "/#", FORECAST_ID);

        return matcher;
    }
}

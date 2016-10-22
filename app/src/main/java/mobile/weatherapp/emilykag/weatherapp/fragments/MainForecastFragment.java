package mobile.weatherapp.emilykag.weatherapp.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.interfaces.Callback;
import mobile.weatherapp.emilykag.weatherapp.models.Forecast;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;
import mobile.weatherapp.emilykag.weatherapp.utils.DateUtils;
import mobile.weatherapp.emilykag.weatherapp.utils.ForecastAsyncTask;
import mobile.weatherapp.emilykag.weatherapp.utils.databaseutils.DatabaseActions;
import mobile.weatherapp.emilykag.weatherapp.utils.databaseutils.WeatherContract;

public class MainForecastFragment extends Fragment implements Callback {

    private TextView textViewLocation;
    private TextView textViewDate;
    private TextView textViewTemp;
    private TextView textViewDesc;
    private TextView textViewToday;
    private TextView textViewWindspeed;
    private TextView textViewHumidity;
    private TextView textViewVisibility;
    private TextView textViewSunrise;
    private TextView textViewSunset;
    private ImageView imageViewWeatherIcon;
    private ProgressBar progressBarMainForecast;
    private LinearLayout linearLayoutMainWeather;
    private TextView textViewLocationNotFound;
    private TextView textViewLastUpdated;
    private SwipeRefreshLayout swipeRefreshLayoutMainForecast;

    private OnFragmentInteractionListener mListener;

    private DatabaseActions databaseActions;

    public MainForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_forecast, container, false);
        textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewTemp = (TextView) view.findViewById(R.id.textViewTemp);
        textViewDesc = (TextView) view.findViewById(R.id.textViewDesc);
        textViewToday = (TextView) view.findViewById(R.id.textViewToday);
        textViewWindspeed = (TextView) view.findViewById(R.id.textViewWindspeed);
        textViewHumidity = (TextView) view.findViewById(R.id.textViewHumidity);
        textViewVisibility = (TextView) view.findViewById(R.id.textViewVisibility);
        textViewSunrise = (TextView) view.findViewById(R.id.textViewSunrise);
        textViewSunset = (TextView) view.findViewById(R.id.textViewSunset);
        imageViewWeatherIcon = (ImageView) view.findViewById(R.id.imageViewWeatherIcon);
        progressBarMainForecast = (ProgressBar) view.findViewById(R.id.progressBarMainForecast);
        linearLayoutMainWeather = (LinearLayout) view.findViewById(R.id.linearLayoutMainWeather);
        textViewLocationNotFound = (TextView) view.findViewById(R.id.textViewLocationNotFound);
        textViewLastUpdated = (TextView) view.findViewById(R.id.textViewLastUpdated);
        swipeRefreshLayoutMainForecast = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMainForecast);

        swipeRefreshLayoutMainForecast.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutMainForecast.setRefreshing(true);
                // do refresh
                updateWeather(1);
                if (swipeRefreshLayoutMainForecast.isRefreshing()) {
                    swipeRefreshLayoutMainForecast.setRefreshing(false);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        onLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void onLoad() {
        databaseActions = new DatabaseActions(getContext());
        WeatherValues weatherValues = databaseActions.retrieveWeatherValues();
        if (weatherValues == null) {
            if (isNetworkAvailable()) {
                updateWeather(2);
            } else {
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.no_internet_message))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        } else {
            setViews(weatherValues, 0);
        }
    }

    private void updateWeather(int type) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                .putString("lastUpdated", DateUtils.formatLastUpdatedDate(Calendar.getInstance().getTime())).apply();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        ForecastAsyncTask forecastAsyncTask = new ForecastAsyncTask(this, progressBarMainForecast, type);
        forecastAsyncTask.execute(location, "c");
    }

    @Override
    public void setViews(WeatherValues weatherValues, int type) {
        if (weatherValues != null) {
            if (type == 0) {
                setWeatherToViews(weatherValues);
            } else if (type == 1) { // update database
                WeatherValues oldWeatherValues = databaseActions.retrieveWeatherValues();
                databaseActions.updateWeatherValues(oldWeatherValues, weatherValues);
                setWeatherToViews(weatherValues);
            } else {
                setWeatherToViews(weatherValues);
                databaseActions.addWeatherValues(weatherValues);
            }
        } else {
            linearLayoutMainWeather.setVisibility(View.GONE);
            textViewLocationNotFound.setVisibility(View.VISIBLE);
            textViewLocationNotFound.setText(R.string.location_not_found);
        }
        textViewLastUpdated.setText(getString(R.string.last_updated, PreferenceManager.getDefaultSharedPreferences(getContext()).getString("lastUpdated", "")));
    }

    @Override
    public void showFailure(int statusCode) {
        String message = "Error " + String.valueOf(statusCode);
        if (!isNetworkAvailable()) {
            message = getString(R.string.no_internet_message);
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setWeatherToViews(WeatherValues weatherValues) {
        textViewLocationNotFound.setVisibility(View.GONE);
        linearLayoutMainWeather.setVisibility(View.VISIBLE);
        textViewLocation.setText(weatherValues.getCity());
        textViewDate.setText(getDate());
        textViewTemp.setText(weatherValues.getTemperature() + " " + (char) 0x00B0 + "C");
        textViewDesc.setText(weatherValues.getNowDescription());
        textViewWindspeed.setText(getString(R.string.windspeed_value, weatherValues.getWindSpeed()));
        textViewHumidity.setText(getString(R.string.humidity_value, weatherValues.getHumidity()));
        textViewVisibility.setText(getString(R.string.visibility_value, weatherValues.getVisibility()));
        textViewSunrise.setText(weatherValues.getSunrise());
        textViewSunset.setText(weatherValues.getSunset());
        textViewToday.setText(weatherValues.getListForecast().get(0).getLow() + (char) 0x00B0 + "C / " +
                weatherValues.getListForecast().get(0).getHigh() + (char) 0x00B0 + "C");
        setWeatherImage(weatherValues.getCode());
        mListener.setWeatherImage(weatherValues.getCode());
        mListener.setWeeklyForecastList(weatherValues.getListForecast());
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.getDefault());
        return df.format(c.getTime());
    }

    private void setWeatherImage(String code) {
        switch (code) {
            case "0":
            case "2":
                setImageResource("tornado");
                break;
            case "1":
            case "3":
            case "4":
            case "37":
            case "38":
            case "39":
            case "45":
            case "47":
                setImageResource("thunderstorms");
                break;
            case "5":
            case "6":
            case "7":
            case "18":
                setImageResource("rainandsnowmixed");
                break;
            case "8":
            case "9":
            case "10":
            case "17":
            case "35":
                setImageResource("rain");
                break;
            case "11":
            case "12":
            case "40":
                setImageResource("shower");
                break;
            case "13":
            case "14":
                setImageResource("flurries");
                break;
            case "15":
            case "16":
            case "41":
            case "42":
            case "43":
            case "46":
                setImageResource("snow");
                break;
            case "19":
            case "20":
            case "21":
            case "22":
                setImageResource("fog");
                break;
            case "23":
            case "24":
                setImageResource("windy");
                break;
            case "25":
                setImageResource("cold");
                break;
            case "26":
            case "44":
                setImageResource("mostlycloudy");
                break;
            case "27":
            case "29":
                setImageResource("mostlyclear");
                break;
            case "28":
            case "30":
                setImageResource("partlysunny");
                break;
            case "31":
            case "33":
                setImageResource("clear");
                break;
            case "32":
            case "34":
                setImageResource("sunny");
                break;
            case "36":
                setImageResource("hot");
                break;
        }
    }

    private void setImageResource(String imgName) {
        int resID = getResources().getIdentifier("drawable/ic_" + imgName, null, getContext().getPackageName());
        imageViewWeatherIcon.setImageResource(resID);
    }

    public boolean isNetworkAvailable() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                getContext().getSystemService(getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null;
    }

    public interface OnFragmentInteractionListener {

        public void setWeeklyForecastList(List<Forecast> listForecast);

        public void setWeatherImage(String code);
    }
}

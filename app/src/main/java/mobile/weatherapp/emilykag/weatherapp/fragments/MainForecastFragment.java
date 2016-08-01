package mobile.weatherapp.emilykag.weatherapp.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import java.util.Locale;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.interfaces.Callback;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;
import mobile.weatherapp.emilykag.weatherapp.utils.ForecastAsyncTask;

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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNetworkAvailable()) {
            updateWeather();
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
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        ForecastAsyncTask forecastAsyncTask = new ForecastAsyncTask(this, progressBarMainForecast);
        forecastAsyncTask.execute(location, "c");
    }

    @Override
    public void setViews(WeatherValues weatherValues) {
        if (weatherValues != null) {
            textViewLocationNotFound.setVisibility(View.GONE);
            linearLayoutMainWeather.setVisibility(View.VISIBLE);
            textViewLocation.setText(weatherValues.getCity());
            textViewDate.setText(getDate());
            textViewTemp.setText(weatherValues.getTemperature() + " " + (char) 0x00B0 + "C");
            textViewDesc.setText(weatherValues.getNowDescription());
            textViewWindspeed.setText(weatherValues.getWindSpeed() + " km/h");
            textViewHumidity.setText(weatherValues.getHumidity() + " %");
            textViewVisibility.setText(weatherValues.getVisibility() + " km");
            textViewSunrise.setText(weatherValues.getSunrise());
            textViewSunset.setText(weatherValues.getSunset());
            textViewToday.setText(weatherValues.getListForecast().get(0).getLow() + (char) 0x00B0 + "C / " +
                    weatherValues.getListForecast().get(0).getHigh() + (char) 0x00B0 + "C");
            setWeatherImage(weatherValues.getCode());
            ForecastFragment.getInstance().setWeatherImage(weatherValues.getCode());
            WeeklyForecastFragment.getInstance().setWeeklyForecastList(weatherValues.getListForecast());
        } else {
            linearLayoutMainWeather.setVisibility(View.GONE);
            textViewLocationNotFound.setVisibility(View.VISIBLE);
            textViewLocationNotFound.setText(R.string.location_not_found);
        }
    }

    @Override
    public void showFailure(int statusCode) {
        Toast.makeText(getContext(), "Error " + String.valueOf(statusCode), Toast.LENGTH_LONG).show();
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.getDefault());
        return df.format(c.getTime());
    }

    private void setWeatherImage(String code) {
        if (code.equals("0") || code.equals("2")) {
            setImageResource("tornado");
        } else if (code.equals("1") || code.equals("3") || code.equals("4") || code.equals("37") || code.equals("38")
                || code.equals("39") || code.equals("45") || code.equals("47")) {
            setImageResource("thunderstorms");
        } else if (code.equals("5") || code.equals("6") || code.equals("7") || code.equals("18")) {
            setImageResource("rainandsnowmixed");
        } else if (code.equals("8") || code.equals("9") || code.equals("10") || code.equals("17") || code.equals("35")) {
            setImageResource("rain");
        } else if (code.equals("11") || code.equals("12") || code.equals("40")) {
            setImageResource("shower");
        } else if (code.equals("13") || code.equals("14")) {
            setImageResource("flurries");
        } else if (code.equals("15") || code.equals("16") || code.equals("41") || code.equals("42") || code.equals("43")
                || code.equals("46")) {
            setImageResource("snow");
        } else if (code.equals("19") || code.equals("20") || code.equals("21") || code.equals("22")) {
            setImageResource("fog");
        } else if (code.equals("23") || code.equals("24")) {
            setImageResource("windy");
        } else if (code.equals("25")) {
            setImageResource("cold");
        } else if (code.equals("26") || code.equals("44")) {
            setImageResource("mostlycloudy");
        } else if (code.equals("27") || code.equals("29")) {
            setImageResource("mostlyclear");
        } else if (code.equals("28") || code.equals("30")) {
            setImageResource("partlysunny");
        } else if (code.equals("31") || code.equals("33")) {
            setImageResource("clear");
        } else if (code.equals("32") || code.equals("34")) {
            setImageResource("sunny");
        } else if (code.equals("36")) {
            setImageResource("hot");
        }
    }

    @SuppressWarnings("deprecation")
    private void setImageResource(String imgName) {
        int resID = getResources().getIdentifier("drawable/ic_" + imgName, null, getContext().getPackageName());
        Drawable weatherIcon = getResources().getDrawable(resID);
        imageViewWeatherIcon.setImageDrawable(weatherIcon);
    }

    public boolean isNetworkAvailable() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                getContext().getSystemService(getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            return false;
        } else {
            return true;
        }
    }
}

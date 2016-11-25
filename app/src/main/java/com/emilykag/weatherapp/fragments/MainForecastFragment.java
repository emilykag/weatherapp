package com.emilykag.weatherapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.models.Forecast;
import com.emilykag.weatherapp.models.Response;
import com.emilykag.weatherapp.models.WeatherValues;
import com.emilykag.weatherapp.service.ForecastService;
import com.emilykag.weatherapp.utils.DateUtils;
import com.emilykag.weatherapp.utils.JSONParser;
import com.emilykag.weatherapp.utils.Tools;
import com.emilykag.weatherapp.utils.WeatherImageTool;
import com.emilykag.weatherapp.utils.databaseutils.DatabaseActions;

import java.util.List;

public class MainForecastFragment extends Fragment {

    private static MainForecastFragment instance;

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

    public static MainForecastFragment getInstance() {
        return instance;
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

        instance = this;

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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(forecastReceiver, new IntentFilter("ForecastService"));
    }

    private void onLoad() {
        databaseActions = new DatabaseActions(getContext());
        WeatherValues weatherValues = databaseActions.retrieveWeatherValues();
        if (weatherValues == null) {
            if (Tools.isNetworkAvailable(getContext())) {
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

    public void updateWeather(int type) {
        Intent intent = new Intent(getContext(), ForecastService.class);
        intent.putExtra("type", type);
        getActivity().startService(intent);
    }

    private BroadcastReceiver forecastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 1);
            Response response = (Response) intent.getSerializableExtra("response");
            if (response != null) {
                if (response.getCode() == 200) {
                    WeatherValues weatherValues = JSONParser.jsonParser(response.getResponse());
                    setViews(weatherValues, type);
                } else {
                    showFailure(response.getCode());
                }
            }
        }
    };

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

    public void showFailure(int statusCode) {
        String message = "Error " + String.valueOf(statusCode);
        if (!Tools.isNetworkAvailable(getContext())) {
            message = getString(R.string.no_internet_message);
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setWeatherToViews(WeatherValues weatherValues) {
        textViewLocationNotFound.setVisibility(View.GONE);
        linearLayoutMainWeather.setVisibility(View.VISIBLE);
        textViewLocation.setText(weatherValues.getCity());
        textViewDate.setText(DateUtils.getDate());
        textViewTemp.setText(weatherValues.getTemperature() + " " + (char) 0x00B0 + "C");
        textViewDesc.setText(weatherValues.getNowDescription());
        textViewWindspeed.setText(getString(R.string.windspeed_value, weatherValues.getWindSpeed()));
        textViewHumidity.setText(getString(R.string.humidity_value, weatherValues.getHumidity()));
        textViewVisibility.setText(getString(R.string.visibility_value, weatherValues.getVisibility()));
        textViewSunrise.setText(weatherValues.getSunrise());
        textViewSunset.setText(weatherValues.getSunset());
        textViewToday.setText(weatherValues.getListForecast().get(0).getLow() + (char) 0x00B0 + "C / " +
                weatherValues.getListForecast().get(0).getHigh() + (char) 0x00B0 + "C");
        new WeatherImageTool(getContext(), imageViewWeatherIcon).setWeatherImage(weatherValues.getCode(), "image");
        mListener.setWeatherImage(weatherValues.getCode());
        mListener.setWeeklyForecastList(weatherValues.getListForecast());
    }

    public interface OnFragmentInteractionListener {

        public void setWeeklyForecastList(List<Forecast> listForecast);

        public void setWeatherImage(String code);
    }
}

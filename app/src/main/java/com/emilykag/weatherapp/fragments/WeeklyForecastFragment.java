package com.emilykag.weatherapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.adapters.WeeklyForecastListAdapter;
import com.emilykag.weatherapp.models.Forecast;

import java.util.List;

public class WeeklyForecastFragment extends Fragment {

    private ListView listViewWeaklyForecast;

    public WeeklyForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
        listViewWeaklyForecast = (ListView) view.findViewById(R.id.listViewWeaklyForecast);

        return view;
    }

    public void setWeeklyForecastList(List<Forecast> listForecast) {
        WeeklyForecastListAdapter adapter = new WeeklyForecastListAdapter(getContext(), listForecast);
        listViewWeaklyForecast.setAdapter(adapter);
    }
}

package mobile.weatherapp.emilykag.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.adapters.WeeklyForecastListAdapter;
import mobile.weatherapp.emilykag.weatherapp.models.Forecast;

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

package com.emilykag.weatherapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.models.Forecast;
import com.emilykag.weatherapp.utils.Tools;
import com.emilykag.weatherapp.utils.WeatherImageTool;

public class WeeklyForecastListAdapter extends ArrayAdapter<Forecast> {

    private List<Forecast> objects;
    private Context context;

    private ImageView imageViewWeaklyForecastLayout;

    public WeeklyForecastListAdapter(Context context, List<Forecast> objects) {
        super(context, R.layout.weakly_forecast_layout, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.weakly_forecast_layout, parent, false);
        }

        Forecast forecast = objects.get(position);
        imageViewWeaklyForecastLayout = (ImageView) convertView.findViewById(R.id.imageViewWeaklyForecastLayout);
        TextView textViewDateWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewDateWeaklyForecastLayout);
        TextView textViewDescWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewDescWeaklyForecastLayout);
        TextView textViewTempWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewTempWeaklyForecastLayout);

        new WeatherImageTool(context, imageViewWeaklyForecastLayout).setWeatherImage(forecast.getCode(), "image");
        textViewDateWeaklyForecastLayout.setText(forecast.getDay() + " " + forecast.getDate());
        textViewDescWeaklyForecastLayout.setText(forecast.getText());
        textViewTempWeaklyForecastLayout.setText(forecast.getLow() + (char) 0x00B0 + "/" + forecast.getHigh() + (char) 0x00B0);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}

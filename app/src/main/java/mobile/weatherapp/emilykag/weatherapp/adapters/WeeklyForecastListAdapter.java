package mobile.weatherapp.emilykag.weatherapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.models.Forecast;

public class WeeklyForecastListAdapter extends ArrayAdapter<Forecast> {

    private List<Forecast> objects;
    private Context context;

    private ImageView imageViewWeaklyForecastLayout;

    public WeeklyForecastListAdapter(Context context, List<Forecast> objects) {
        super(context, R.layout.weakly_forecast_layout, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.weakly_forecast_layout, parent, false);

        Forecast forecast = objects.get(position);
        imageViewWeaklyForecastLayout = (ImageView) convertView.findViewById(R.id.imageViewWeaklyForecastLayout);
        TextView textViewDateWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewDateWeaklyForecastLayout);
        TextView textViewDescWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewDescWeaklyForecastLayout);
        TextView textViewTempWeaklyForecastLayout = (TextView) convertView.findViewById(R.id.textViewTempWeaklyForecastLayout);

        setWeatherImage(forecast.getCode());
        textViewDateWeaklyForecastLayout.setText(forecast.getDay() + " " + forecast.getDate());
        textViewDescWeaklyForecastLayout.setText(forecast.getText());
        textViewTempWeaklyForecastLayout.setText(forecast.getLow() + (char) 0x00B0 + "/" + forecast.getHigh() + (char) 0x00B0);

        return convertView;
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

    private void setImageResource(String imgName) {
        int resID = context.getResources().getIdentifier("drawable/ic_" + imgName, null, getContext().getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIcon = context.getResources().getDrawable(resID);
        imageViewWeaklyForecastLayout.setImageDrawable(weatherIcon);
    }
}

package mobile.weatherapp.emilykag.weatherapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.weakly_forecast_layout, parent, false);
        }

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

    @Override
    public boolean isEnabled(int position) {
        return false;
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
        int resID = context.getResources().getIdentifier("drawable/ic_" + imgName, null, getContext().getPackageName());
        imageViewWeaklyForecastLayout.setImageResource(resID);
    }
}

package com.emilykag.weatherapp.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.emilykag.weatheapp.R;

public class WeatherImageTool {

    private Context context;
    private ImageView imageViewWeatherIcon;
    private LinearLayout layoutFragmentForecast;
    private RemoteViews views;

    public WeatherImageTool(Context context, ImageView imageViewWeatherIcon) {
        this.context = context;
        this.imageViewWeatherIcon = imageViewWeatherIcon;
    }

    public WeatherImageTool(Context context, LinearLayout layoutFragmentForecast) {
        this.context = context;
        this.layoutFragmentForecast = layoutFragmentForecast;
    }

    public WeatherImageTool(Context context, RemoteViews views) {
        this.context = context;
        this.views = views;
    }

    public void setWeatherImage(String code, String type) {
        String imgRes = "";
        switch (code) {
            case "0":
            case "2":
                imgRes = "tornado";
                break;
            case "1":
            case "3":
            case "4":
            case "37":
            case "38":
            case "39":
            case "45":
            case "47":
                imgRes = "thunderstorms";
                break;
            case "5":
            case "6":
            case "7":
            case "18":
                imgRes = "rainandsnowmixed";
                break;
            case "8":
            case "9":
            case "10":
            case "17":
            case "35":
                imgRes = "rain";
                break;
            case "11":
            case "12":
            case "40":
                imgRes = "shower";
                break;
            case "13":
            case "14":
                imgRes = "flurries";
                break;
            case "15":
            case "16":
            case "41":
            case "42":
            case "43":
            case "46":
                imgRes = "snow";
                break;
            case "19":
            case "20":
            case "21":
            case "22":
                imgRes = "fog";
                break;
            case "23":
            case "24":
                imgRes = "windy";
                break;
            case "25":
                imgRes = "cold";
                break;
            case "26":
            case "44":
                imgRes = "mostlycloudy";
                break;
            case "27":
            case "29":
                imgRes = "mostlyclear";
                break;
            case "28":
            case "30":
                imgRes = "partlysunny";
                break;
            case "31":
            case "33":
                imgRes = "clear";
                break;
            case "32":
            case "34":
                imgRes = "sunny";
                break;
            case "36":
                imgRes = "hot";
                break;
        }
        switch (type) {
            case "back":
                setBackResource(imgRes);
                break;
            case "image":
                setImageResource(imgRes);
                break;
            case "widget":
                setWidgetImageResource(imgRes);
                break;
            case "widgetBack":
                setWidgetBackResource(imgRes);
                break;
        }
    }

    private void setBackResource(String imgName) {
        int bgID = context.getResources().getIdentifier("drawable/bg_" + imgName, null, context.getPackageName());
        layoutFragmentForecast.setBackgroundResource(bgID);
    }

    private void setImageResource(String imgName) {
        int resID = context.getResources().getIdentifier("drawable/ic_" + imgName, null, context.getPackageName());
        imageViewWeatherIcon.setImageResource(resID);
    }

    private void setWidgetImageResource(String imgName) {
        int resID = context.getResources().getIdentifier("drawable/ic_" + imgName, null, context.getPackageName());
        views.setImageViewResource(R.id.imageViewWeatherIcon, resID);
    }

    private void setWidgetBackResource(String imgName) {
        int resID = context.getResources().getIdentifier("drawable/widget_" + imgName, null, context.getPackageName());
        views.setInt(R.id.linearLayoutWidgetBackground, "setBackgroundResource", resID);
    }
}

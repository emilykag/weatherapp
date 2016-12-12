package com.emilykag.weatherapp.interfaces;

import com.emilykag.weatherapp.models.WeatherValues;

public interface Callback {

    public void setViews(WeatherValues weatherValues, int type);

    public void showFailure(int statusCode);
}

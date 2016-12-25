package com.emilykag.weatherapp.models;

public class WeatherResponse {

    private int responseCode;
    private WeatherValues weatherValues;

    public WeatherResponse(int responseCode, WeatherValues weatherValues) {
        this.responseCode = responseCode;
        this.weatherValues = weatherValues;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public WeatherValues getWeatherValues() {
        return weatherValues;
    }
}

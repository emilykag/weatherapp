package com.emilykag.weatherapp.models;

import java.io.Serializable;

public class Forecast implements Serializable {

    private String _id;
    private String code;
    private String date;
    private String day;
    private String high;
    private String low;
    private String text;

    public Forecast() {
    }

    public Forecast(String _id, String code, String date, String day, String high, String low, String text) {
        this._id = _id;
        this.code = code;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
    }

    public Forecast(String code, String date, String day, String high, String low, String text) {
        this.code = code;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
    }

    public String get_id() {
        return _id;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getText() {
        return text;
    }
}
package mobile.weatherapp.emilykag.weatherapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherValues implements Serializable {

    private int count;
    private String city;
    private String windSpeed;
    private String humidity;
    private String visibility;
    private String sunrise;
    private String sunset;
    private String code;
    private String temperature;
    private String nowDescription;
    private ArrayList<Forecast> listForecast;

    public WeatherValues() {
    }

    public WeatherValues(int count, String city, String windSpeed, String humidity, String visibility, String sunrise,
                         String sunset, String code, String temperature, String nowDescription, ArrayList<Forecast> listForecast) {
        this.count = count;
        this.city = city;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.visibility = visibility;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.code = code;
        this.temperature = temperature;
        this.nowDescription = nowDescription;
        this.listForecast = listForecast;
    }

    public int getCount() {
        return count;
    }

    public String getCity() {
        return city;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getCode() {
        return code;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getNowDescription() {
        return nowDescription;
    }

    public ArrayList<Forecast> getListForecast() {
        return listForecast;
    }
}

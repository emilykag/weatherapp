package mobile.weatherapp.emilykag.weatherapp.interfaces;

import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

public interface Callback {

    public void setViews(WeatherValues weatherValues, int type);

    public void showFailure(int statusCode);
}

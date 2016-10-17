package mobile.weatherapp.emilykag.weatherapp.interfaces;

import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

public interface Callback {

    public void setViews(WeatherValues weatherValues, boolean isSaved);

    public void showFailure(int statusCode);
}

package mobile.weatherapp.emilykag.weatherapp.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mobile.weatherapp.emilykag.weatherapp.models.Forecast;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

public class JSONParser {

    public static WeatherValues jsonParser(String forecastJsonStr) {

        WeatherValues weatherValues = null;

        try {
            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            // first level
            JSONObject query = forecastJson.getJSONObject("query");

            int count = query.getInt("count");
            if (count == 1) {

                // second level
                JSONObject results = query.getJSONObject("results");

                // third level
                JSONObject channel = results.getJSONObject("channel");

                // location is inside channel (fourth level)
                JSONObject location = channel.getJSONObject("location");
                String city = location.getString("city");

                // wind is inside channel (fourth level)
                JSONObject wind = channel.getJSONObject("wind");
                String windSpeed = wind.getString("speed");

                // atmosphere is inside channel (fourth level)
                JSONObject atmosphere = channel.getJSONObject("atmosphere");
                String humidity = atmosphere.getString("humidity");
                String visibility = atmosphere.getString("visibility");

                // astronomy is inside channel (fourth level)
                JSONObject astronomy = channel.getJSONObject("astronomy");
                String sunrise = astronomy.getString("sunrise");
                String sunset = astronomy.getString("sunset");

                // item is inside channel (fourth level)
                JSONObject item = channel.getJSONObject("item");

                // condition is inside item (fifth level)
                JSONObject condition = item.getJSONObject("condition");
                String code = condition.getString("code");
                String temperature = condition.getString("temp");
                String nowDescription = condition.getString("text");

                // forecast is inside item (fifth level) and it's an array
                JSONArray forecastArray = item.getJSONArray("forecast");
                ArrayList<Forecast> listForecast = new ArrayList<Forecast>();
                Forecast objForecast;
                for (int i = 0; i < forecastArray.length(); i++) {
                    JSONObject forecast = forecastArray.getJSONObject(i);
                    objForecast = new Forecast(forecast.getString("code"), forecast.getString("date"), forecast.getString("day"),
                            forecast.getString("high"), forecast.getString("low"), forecast.getString("text"));
                    listForecast.add(objForecast);
                }

                weatherValues = new WeatherValues(count, city, windSpeed, humidity, visibility,
                        sunrise, sunset, code, temperature, nowDescription, listForecast);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherValues;

    }

}

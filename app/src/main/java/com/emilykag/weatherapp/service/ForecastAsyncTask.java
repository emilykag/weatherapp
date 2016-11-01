package com.emilykag.weatherapp.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.emilykag.weatherapp.interfaces.Callback;
import com.emilykag.weatherapp.models.Response;
import com.emilykag.weatherapp.utils.JSONParser;
import com.emilykag.weatherapp.utils.UrlConnection;
import com.emilykag.weatherapp.models.WeatherValues;

public class ForecastAsyncTask extends AsyncTask<String, Void, Response> {

    private Callback callback;
    private ProgressBar progressBar;
    private int type;

    public ForecastAsyncTask(Callback callback, ProgressBar progressBar, int type) {
        this.callback = callback;
        this.progressBar = progressBar;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Response doInBackground(String... params) {
        if (params.length == 0)
            return null;

        return UrlConnection.getHttpResponse(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(Response forecastJsonStr) {
        if (forecastJsonStr.getCode() == 200) {
            System.out.println(forecastJsonStr.getResponse());
            WeatherValues weatherValues = JSONParser.jsonParser(forecastJsonStr.getResponse());
            callback.setViews(weatherValues, type);
        } else {
            callback.showFailure(forecastJsonStr.getCode());
        }
        progressBar.setVisibility(View.GONE);
    }
}

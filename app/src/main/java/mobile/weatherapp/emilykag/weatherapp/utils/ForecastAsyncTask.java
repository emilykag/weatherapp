package mobile.weatherapp.emilykag.weatherapp.utils;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import mobile.weatherapp.emilykag.weatherapp.interfaces.Callback;
import mobile.weatherapp.emilykag.weatherapp.models.Response;
import mobile.weatherapp.emilykag.weatherapp.models.WeatherValues;

public class ForecastAsyncTask extends AsyncTask<String, Void, Response> {

    private Callback callback;
    private ProgressBar progressBar;

    public ForecastAsyncTask(Callback callback, ProgressBar progressBar) {
        this.callback = callback;
        this.progressBar = progressBar;
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
            WeatherValues weatherValues = JSONParser.jsonParser(forecastJsonStr.getResponse());
            callback.setViews(weatherValues, false);
        } else {
            callback.showFailure(forecastJsonStr.getCode());
        }
        progressBar.setVisibility(View.GONE);
    }
}

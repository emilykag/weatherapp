package com.emilykag.weatherapp.service.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WeatherAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private WeatherAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new WeatherAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}

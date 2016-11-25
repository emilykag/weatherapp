package com.emilykag.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tools {

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null;
    }
}

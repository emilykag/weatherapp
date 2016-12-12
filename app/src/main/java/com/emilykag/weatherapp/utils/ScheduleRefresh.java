package com.emilykag.weatherapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.emilykag.weatherapp.service.StartForecastServiceReceiver;

public class ScheduleRefresh {

    public static void scheduleRefresh(Context context, int savedInterval) {
        Intent intent = new Intent(context, StartForecastServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        int interval = 2 * 60000; // 2 minutes. 1 hour = 60 minutes
        //int oneHourInterval = 60 * 60000;
        //int interval = savedInterval * 60 * 60000;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis(),
                interval, pendingIntent);
    }

    public static void stopScheduledRefresh(Context context) {
        Intent intent = new Intent(context, StartForecastServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}

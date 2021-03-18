package com.example.android.sqliteweather;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm extends BroadcastReceiver {

    void setAlarm(String s_date, Context context) throws ParseException {
        AlarmManager alarm;
        PendingIntent pendingIntent;

        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //convert string date from API to epoch
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = s.parse(s_date);
        long mil = date.getTime();

        //long mil = System.currentTimeMillis();

        alarm.set(AlarmManager.RTC_WAKEUP, mil, pendingIntent);
        Log.d("alarm", "Alarm is set for flight time");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager p = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wake = p.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wake");
        wake.acquire();

        Toast.makeText(context, "Flight!", Toast.LENGTH_LONG).show();

        wake.release();
    }
}

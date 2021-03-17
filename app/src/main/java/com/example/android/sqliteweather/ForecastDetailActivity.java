package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.sqliteweather.data.ForecastCity;
import com.example.android.sqliteweather.data.FlightData;
import com.example.android.sqliteweather.utils.OpenWeatherUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.android.sqliteweather.Alarm;


public class ForecastDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FORECAST_DATA = "ForecastDetailActivity.ForecastData";
    public static final String EXTRA_FORECAST_CITY = "ForecastDetailActivity.ForecastCity";

    private FlightData flightData = null;
    private ForecastCity forecastCity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_FORECAST_CITY)) {
            this.forecastCity = (ForecastCity)intent.getSerializableExtra(EXTRA_FORECAST_CITY);
            TextView forecastCityTV = findViewById(R.id.tv_forecast_city);
            forecastCityTV.setText(this.forecastCity.getName());
        }

        if (intent != null && intent.hasExtra(EXTRA_FORECAST_DATA)) {
            this.flightData = (FlightData)intent.getSerializableExtra(EXTRA_FORECAST_DATA);

            /*
             * Load forecast icon into ImageView using Glide: https://bumptech.github.io/glide/
             */
            ImageView forecastIconIV = findViewById(R.id.iv_forecast_icon);
            Glide.with(this)
                    .load(this.flightData.getIconUrl())
                    .into(forecastIconIV);

            TextView forecastDateTV = findViewById(R.id.tv_forecast_date);
            Calendar date = OpenWeatherUtils.dateFromEpochAndTZOffset(
                    flightData.getDest(),
                    forecastCity.getTimezoneOffsetSeconds()
            );
            forecastDateTV.setText(getString(
                    R.string.forecast_date_time,
                    getString(R.string.forecast_date, date),
                    getString(R.string.forecast_time, date)
            ));

            String unitsPref = sharedPreferences.getString(
                    getString(R.string.pref_units_key),
                    getString(R.string.pref_units_default_value)
            );
            TextView lowTempTV = findViewById(R.id.tv_low_temp);
            lowTempTV.setText(getString(
                    R.string.forecast_temp,
                    flightData.getTime(),
                    /* get correct temperature unit for unit preference setting */
                    OpenWeatherUtils.getTemperatureDisplayForUnitsPref(unitsPref, this)
            ));

            TextView highTempTV = findViewById(R.id.tv_high_temp);
            highTempTV.setText(getString(
                    R.string.forecast_temp,
                    flightData.getDept(),
                    /* get correct temperature unit for unit preference setting */
                    OpenWeatherUtils.getTemperatureDisplayForUnitsPref(unitsPref, this)
            ));

            TextView popTV = findViewById(R.id.tv_pop);
            popTV.setText(getString(R.string.forecast_pop, flightData.getStatus()));

            TextView cloudsTV = findViewById(R.id.tv_clouds);
            cloudsTV.setText(getString(R.string.forecast_clouds, flightData.getLine()));

            TextView windTV = findViewById(R.id.tv_wind);
            windTV.setText(getString(
                    R.string.forecast_wind,
                    flightData.getNum(),
                    /* get correct wind speed unit for unit preference setting */
                    OpenWeatherUtils.getWindSpeedDisplayForUnitsPref(unitsPref, this)
            ));

            ImageView windDirIV = findViewById(R.id.iv_wind_dir);
            windDirIV.setRotation(flightData.getWindDirDeg());

            TextView forecastDescriptionTV = findViewById(R.id.tv_forecast_description);
            forecastDescriptionTV.setText(flightData.getShortDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_forecast_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                Alarm a = new Alarm();
                String date = null;
                //date = getScheduled();
                a.setAlarm(date,this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




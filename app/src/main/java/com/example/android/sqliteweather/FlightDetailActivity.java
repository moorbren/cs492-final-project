package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FLIGHT_DATA = "FlightDetailActivity.RealtimeFlightData";
    //public static final String EXTRA_FORECAST_CITY = "FlightDetailActivity.ForecastCity";

    private RealtimeFlightDataContainer.RealtimeFlightData flightData = null;
    ImageView imgClick;
    //private ForecastCity forecastCity = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail); //sets layout to specified XML
        imgClick = (ImageView)findViewById(R.id.itm_alarm);

        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flightdetailtest", "You need to set an alarm when you see this!");
            }
        });
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();

        /*if (intent != null && intent.hasExtra(EXTRA_FORECAST_CITY)) {
            this.forecastCity = (ForecastCity)intent.getSerializableExtra(EXTRA_FORECAST_CITY);
            TextView forecastCityTV = findViewById(R.id.tv_forecast_city);
            forecastCityTV.setText(this.forecastCity.getName());
        }*/

        if (intent != null && intent.hasExtra(EXTRA_FLIGHT_DATA)) {
            this.flightData = (RealtimeFlightDataContainer.RealtimeFlightData) intent.getSerializableExtra(EXTRA_FLIGHT_DATA);

            /*ImageView forecastIconIV = findViewById(R.id.iv_forecast_icon);
            Glide.with(this)
                    .load(this.forecastData.getIconUrl())
                    .into(forecastIconIV);*/

            //TextView forecastDateTV = findViewById(R.id.tv_forecast_date);
            /*Calendar date = OpenWeatherUtils.dateFromEpochAndTZOffset(
                    forecastData.getEpoch(),
                    forecastCity.getTimezoneOffsetSeconds()
            );*/
            /*
            forecastDateTV.setText(getString(
                    R.string.forecast_date_time,
                    getString(R.string.forecast_date, date),
                    getString(R.string.forecast_time, date)
            ));
            */
            /*
            String unitsPref = sharedPreferences.getString(
                    getString(R.string.pref_units_key),
                    getString(R.string.pref_units_default_value)
            );*/
            TextView departureTV = findViewById(R.id.tv_departure_detailed);
            departureTV.setText(
                    this.flightData.departure.getAirport()
            );

            TextView arrivalTV = findViewById(R.id.tv_arrival_detailed);
            arrivalTV.setText(this.flightData.arrival.getAirport());

            TextView airlineTV = findViewById(R.id.tv_airline_detailed);
            airlineTV.setText(this.flightData.airline.getName());

            TextView flightnoTV = findViewById(R.id.tv_flightno_detailed);
            flightnoTV.setText(this.flightData.flight.getNumber());

            TextView departtimeTV = findViewById(R.id.tv_departtime_detailed);
            departtimeTV.setText(
                    LocalDateTime.parse(
                            this.flightData.departure.getScheduled(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    ).format(
                            DateTimeFormatter.ofPattern("MMM d uuuu H:mm")
                    )
            );

            TextView departureHeader = findViewById(R.id.tv_departure_header);
            departureHeader.setText("Departure Airport");
            TextView arrivalHeader = findViewById(R.id.tv_arrival_header);
            arrivalHeader.setText("Arrival Airport");
            TextView airlineHeader = findViewById(R.id.tv_airline_header);
            airlineHeader.setText("Airline");
            TextView flightnoHeader = findViewById(R.id.tv_flightno_header);
            flightnoHeader.setText("Flight Number");
            TextView departtimeHeader = findViewById(R.id.tv_departtime_header);
            departtimeHeader.setText("Departure Time");
            TextView setAlarm = findViewById(R.id.tv_setalarm);
            setAlarm.setText("Set an alarm for this flight");
            /*
            ImageView windDirIV = findViewById(R.id.iv_wind_dir);
            windDirIV.setRotation(flightData.getWindDirDeg());

            TextView forecastDescriptionTV = findViewById(R.id.tv_forecast_description);
            forecastDescriptionTV.setText(flightData.getShortDescription());*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_flight_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                //shareForecastText();
                Log.d("flightdetailtest", "You need to add this to the database when you see this!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * This method uses an implicit intent to launch the Android Sharesheet to allow the user to
     * share the current forecast.
     */
    /*
    private void shareForecastText() {
        if (this.forecastData != null) { //&& this.forecastCity != null
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Calendar date = OpenWeatherUtils.dateFromEpochAndTZOffset(
                    forecastData.getEpoch(),
                    //forecastCity.getTimezoneOffsetSeconds()
            );
            String unitsPref = sharedPreferences.getString(
                    getString(R.string.pref_units_key),
                    getString(R.string.pref_units_default_value)
            );
            String shareText = getString(
                    R.string.share_forecast_text,
                    getString(R.string.app_name),
                    //this.forecastCity.getName(),
                    "dummy",
                    getString(
                            R.string.forecast_date_time,
                            getString(R.string.forecast_date, date),
                            getString(R.string.forecast_time, date)
                    ),
                    this.forecastData.getShortDescription(),
                    getString(
                            R.string.forecast_temp,
                            forecastData.getHighTemp(),
                            OpenWeatherUtils.getTemperatureDisplayForUnitsPref(unitsPref, this)
                    ),
                    getString(
                            R.string.forecast_temp,
                            forecastData.getLowTemp(),
                            OpenWeatherUtils.getTemperatureDisplayForUnitsPref(unitsPref, this)
                    ),
                    getString(R.string.forecast_pop, this.forecastData.getPop())
            );

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sendIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(sendIntent, null);
            startActivity(chooserIntent);
        }
    }*/
}
package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sqliteweather.data.FavoritedFlights;
import com.example.android.sqliteweather.data.FavoritedFlightsRepository;
import com.example.android.sqliteweather.data.FavoritedFlightsViewModel;
import com.example.android.sqliteweather.data.LoadingStatus;
import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;
import com.google.gson.Gson;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity
        implements FlightDataAdapter.OnFlightItemClickListener,
            SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    private String currentDepIata = "LAX";
    private String currentArrIata = "JFK";

    private EditText dair;
    private EditText aair;

    private FlightDataAdapter flightDataAdapter;
    private FlightDataViewModel flightDataViewModel;

    private RecyclerView flightListRv;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    FavoritedFlightsViewModel favoritedFlightsViewModel;
    private List<FavoritedFlights> currentFavoritedFlights;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dair = findViewById(R.id.et_departure_search_box);
        this.aair = findViewById(R.id.et_destination_search_box);
        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);


        this.flightListRv = findViewById(R.id.rv_flight_list);
        this.flightListRv.setLayoutManager(new LinearLayoutManager(this));
        this.flightListRv.setHasFixedSize(true);

        this.flightDataAdapter = new FlightDataAdapter(this);
        this.flightListRv.setAdapter(this.flightDataAdapter);
        this.flightListRv.setItemAnimator(new DefaultItemAnimator());

        this.favoritedFlightsViewModel = new FavoritedFlightsViewModel(getApplication());

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        this.flightDataViewModel = new ViewModelProvider(this)
                .get(FlightDataViewModel.class);

        this.loadFlights();

        initObservers();

//        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
//        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.searchbtn_light);
    }

    @Override
    public void onFlightItemClick(RealtimeFlightDataContainer.RealtimeFlightData flightData) {
        Intent intent = new Intent(this, FlightDetailActivity.class);
        intent.putExtra(FlightDetailActivity.EXTRA_FLIGHT_DATA, flightData);
        //intent.putExtra(FlightDetailActivity.EXTRA_FORECAST_CITY, this.forecastCity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        this.currentDepIata = this.dair.getText().toString();
        this.currentArrIata = this.aair.getText().toString();

        int id = item.getItemId();

        if (id == android.R.id.home) {
            loadFlights();
            return true;

        } else if (id == R.id.saved_flights_button){
            Intent i = new Intent(this, SavedFlightsActivity.class);
            i.putExtra(SavedFlightsActivity.EXTRA_FLIGHT_DATA, new Gson().toJson(currentFavoritedFlights));
            this.startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String city = sharedPreferences.getString("pref_location", "Corvallis");
        String date = String.valueOf(LocalDateTime.now());
        //CitySearch search = new CitySearch();
        //search.city = city;
        //search.date = date;
        //Log.d(TAG, "SEARCH: " + search.city + " || " + search.date);

        this.loadFlights();
    }

    private void initObservers(){
        /*
         * Update UI to reflect newly fetched forecast data.
         */
        this.flightDataViewModel.getRealtimeFlightDataContainer().observe(
                this,
                new Observer<RealtimeFlightDataContainer>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onChanged(RealtimeFlightDataContainer realtimeFlightDataContainer) {

                        if (realtimeFlightDataContainer != null && realtimeFlightDataContainer.data != null) {
                            Log.d(TAG, realtimeFlightDataContainer.data.length + "");
                            if (realtimeFlightDataContainer.data.length < 1){
                                Log.e(TAG, "No search results found");
                            }
                            else {
                                flightDataAdapter.updateFlightData(realtimeFlightDataContainer);

                                //forecastCity = fiveDayForecast.getForecastCity();
                                ActionBar actionBar = getSupportActionBar();
                                actionBar.setTitle(realtimeFlightDataContainer.data[0].departure.getAirport());
                            }
                        }
                    }
                }
        );

        /*
         * Update UI to reflect changes in loading status.
         */
        this.flightDataViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {
                            Log.d(TAG, "Recycler view should be visible.");
                            loadingIndicatorPB.setVisibility(View.GONE);
                            flightListRv.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.GONE);
                        } else {
                            loadingIndicatorPB.setVisibility(View.GONE);
                            flightListRv.setVisibility(View.GONE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                            errorMessageTV.setText(getString(R.string.loading_error, "ヽ(。_°)ノ"));
                        }
                    }
                }
        );

        this.favoritedFlightsViewModel.getAllFavorites().observe(this, new Observer<List<FavoritedFlights>>() {
            @Override
            public void onChanged(List<FavoritedFlights> favoritedFlights) {
                currentFavoritedFlights = favoritedFlights;
            }
        });

    }


    private void loadFlights() {
        this.flightDataViewModel.loadFlight(currentDepIata, currentArrIata, "");
    }

}
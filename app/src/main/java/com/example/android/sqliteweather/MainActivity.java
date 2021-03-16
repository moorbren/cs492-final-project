package com.example.android.sqliteweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sqliteweather.data.CitySearch;
import com.example.android.sqliteweather.data.FiveDayForecast;
import com.example.android.sqliteweather.data.ForecastCity;
import com.example.android.sqliteweather.data.ForecastData;
import com.example.android.sqliteweather.data.LoadingStatus;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDateTime;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ForecastAdapter.OnForecastItemClickListener,
            SharedPreferences.OnSharedPreferenceChangeListener,
            CitySearchAdapter.OnCitySearchItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
     * To use your own OpenWeather API key, create a file called `gradle.properties` in your
     * GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in MacOS/Linux and
     * `$USER_HOME/.gradle/` in Windows), and add the following line:
     *
     *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
     *
     * The Gradle build for this project is configured to automatically grab that value and store
     * it in the field `BuildConfig.OPENWEATHER_API_KEY` that's used below.  You can read more
     * about this setup on the following pages:
     *
     *   https://developer.android.com/studio/build/gradle-tips#share-custom-fields-and-resource-values-with-your-app-code
     *
     *   https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties
     *
     * Alternatively, you can just hard-code your API key below 🤷‍.  If you do hard code your API
     * key below, make sure to get rid of the following line (line 18) in build.gradle:
     *
     *   buildConfigField("String", "OPENWEATHER_API_KEY", OPENWEATHER_API_KEY)
     */
//    private static final String OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY;
    private static final String OPENWEATHER_APPID = "4c01bcf287f5d2b193d2b41f01597810";

    private ForecastAdapter forecastAdapter;
    private FiveDayForecastViewModel fiveDayForecastViewModel;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ForecastCity forecastCity;

    private RecyclerView forecastListRV;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    private DrawerLayout drawerLayout;
    private CitySearchViewModel citySearchViewModel;

    private RecyclerView mCitySearchRV;
    private CitySearchAdapter mCitySearchAdapter;
    private CitySearchViewModel mCitySearchViewmodel;

    private Toast errorToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);

        this.drawerLayout = findViewById(R.id.drawer_layout);

        this.forecastListRV = findViewById(R.id.rv_forecast_list);
        this.forecastListRV.setLayoutManager(new LinearLayoutManager(this));
        this.forecastListRV.setHasFixedSize(true);

        this.forecastAdapter = new ForecastAdapter(this);
        this.forecastListRV.setAdapter(this.forecastAdapter);

        this.mCitySearchRV = findViewById(R.id.rv_cities_list);
        this.mCitySearchRV.setLayoutManager(new LinearLayoutManager(this));
        this.mCitySearchRV.setHasFixedSize(true);

        this.mCitySearchAdapter = new CitySearchAdapter(this);
        this.mCitySearchRV.setAdapter(this.mCitySearchAdapter);
        this.mCitySearchViewmodel = new CitySearchViewModel(getApplication());



        this.citySearchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CitySearchViewModel.class);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        this.fiveDayForecastViewModel = new ViewModelProvider(this)
                .get(FiveDayForecastViewModel.class);
        this.loadForecast();

        /*
         * Update UI to reflect newly fetched forecast data.
         */
        this.fiveDayForecastViewModel.getFiveDayForecast().observe(
                this,
                new Observer<FiveDayForecast>() {
                    @Override
                    public void onChanged(FiveDayForecast fiveDayForecast) {
                        forecastAdapter.updateForecastData(fiveDayForecast);
                        if (fiveDayForecast != null) {
                            forecastCity = fiveDayForecast.getForecastCity();
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setTitle(forecastCity.getName());
                        }
                    }
                }
        );

        /*
         * Update UI to reflect changes in loading status.
         */
        this.fiveDayForecastViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            forecastListRV.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);
                        } else {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            forecastListRV.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                            errorMessageTV.setText(getString(R.string.loading_error, "ヽ(。_°)ノ"));
                        }
                    }
                }
        );

        this.mCitySearchViewmodel.getAllCities().observe(
                this,
                new Observer<List<CitySearch>>() {
                    @Override
                    public void onChanged(List<CitySearch> citySearches) {
                        mCitySearchAdapter.updateCitySearch(citySearches);
                    }
                }
        );

//        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
//        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCitySearchItemClick(CitySearch citySearch) {
        this.drawerLayout.closeDrawers();
        citySearch.date = String.valueOf(LocalDateTime.now());

        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("pref_location",citySearch.city);
        editor.apply();

        this.citySearchViewModel.insertSearchCity(citySearch);
        this.loadForecast();
        Log.d(TAG, "LOADED");


    }

    @Override
    public void onForecastItemClick(ForecastData forecastData) {
        Intent intent = new Intent(this, ForecastDetailActivity.class);
        intent.putExtra(ForecastDetailActivity.EXTRA_FORECAST_DATA, forecastData);
        intent.putExtra(ForecastDetailActivity.EXTRA_FORECAST_CITY, this.forecastCity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                viewForecastCityInMap();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

//    @Override
//    public boolean onNavigationItemSelected (@NonNull MenuItem item){
//        this.drawerLayout.closeDrawers();
//        switch (item.getItemId()) {
//            case R.id.nav_search:
//                return true;
////            case R.id.nav_bookmarked_repos:
////                Intent bookmarkedReposIntent = new Intent(this,BookmarkedRepos.class);
////                startActivity(bookmarkedReposIntent);
////                return true;
//            case R.id.nav_settings:
//                Intent settingsIntent = new Intent (this,SettingsActivity.class);
//                startActivity(settingsIntent);
//                return true;
//            default:
//                return false;
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String city = sharedPreferences.getString("pref_location", "Corvallis");
        String date = String.valueOf(LocalDateTime.now());
        CitySearch search = new CitySearch();
        search.city = city;
        search.date = date;
        Log.d(TAG, "SEARCH: " + search.city + " || " + search.date);

        this.citySearchViewModel.insertSearchCity(search);
        this.loadForecast();
    }

    /**
     * Triggers a new forecast to be fetched based on current preference values.
     */
    private void loadForecast() {
        this.fiveDayForecastViewModel.loadForecast(
                this.sharedPreferences.getString(
                        getString(R.string.pref_location_key),
                        "Corvallis,OR,US"
                ),
                this.sharedPreferences.getString(
                        getString(R.string.pref_units_key),
                        getString(R.string.pref_units_default_value)
                ),
                OPENWEATHER_APPID
        );
    }

    /**
     * This function uses an implicit intent to view the forecast city in a map.
     */
    private void viewForecastCityInMap() {
        if (this.forecastCity != null) {
            Uri forecastCityGeoUri = Uri.parse(getString(
                    R.string.geo_uri,
                    this.forecastCity.getLatitude(),
                    this.forecastCity.getLongitude(),
                    12
            ));
            Intent intent = new Intent(Intent.ACTION_VIEW, forecastCityGeoUri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                if (this.errorToast != null) {
                    this.errorToast.cancel();
                }
                this.errorToast = Toast.makeText(
                        this,
                        getString(R.string.action_map_error),
                        Toast.LENGTH_LONG
                );
                this.errorToast.show();
            }
        }
    }
}
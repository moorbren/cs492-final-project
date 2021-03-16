package com.example.android.sqliteweather.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlightDataRepository {
    private static final String TAG = FlightDataRepository.class.getSimpleName();
    private static final String BASE_URL = "https://api.aviationstack.com/v1/flights/";

    private MutableLiveData<RealtimeFlightDataContainer> realtimeFlightDataContainer;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentLocation;
    private String currentUnits;

    private AviationStackService aviationStackService;

    public FlightDataRepository() {
        this.realtimeFlightDataContainer = new MutableLiveData<>();
        this.realtimeFlightDataContainer.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.aviationStackService = retrofit.create(AviationStackService.class);
    }

    public LiveData<RealtimeFlightDataContainer> getFiveDayForecast() {
        return this.realtimeFlightDataContainer;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadFlight(String departureCity, String arrivalCity, String flightDate, String apiKey) {
        if (shouldFetchForecast(departureCity, arrivalCity)) {
            Log.d(TAG, "fetching new forecast data for cities: " + arrivalCity + " " + departureCity + ", flightDate: " + flightDate);

            //this.currentLocation = location;
            //this.currentUnits = units;
            this.realtimeFlightDataContainer.setValue(null);
            this.loadingStatus.setValue(LoadingStatus.LOADING);

            Call<RealtimeFlightDataContainer> req = this.aviationStackService.fetchFlight(arrivalCity, departureCity, flightDate, apiKey);
            req.enqueue(new Callback<RealtimeFlightDataContainer>() {
                @Override
                public void onResponse(Call<RealtimeFlightDataContainer> call, Response<RealtimeFlightDataContainer> response) {
                    if (response.code() == 200) {
                        realtimeFlightDataContainer.setValue(response.body());
                        loadingStatus.setValue(LoadingStatus.SUCCESS);
                    } else {
                        loadingStatus.setValue(LoadingStatus.ERROR);
                        Log.d(TAG, "unsuccessful API request: " + call.request().url());
                        Log.d(TAG, "  -- response status code: " + response.code());
                        Log.d(TAG, "  -- response: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<RealtimeFlightDataContainer> call, Throwable t) {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    t.printStackTrace();
                }
            });
        } else {
            Log.d(TAG, "using cached forecast data for cities: " + arrivalCity + " " + departureCity + ", flightDate: " + flightDate);
        }
    }

    private boolean shouldFetchForecast(String location, String units) {
        /*
         * Fetch forecast if there isn't currently one stored.
         */
        RealtimeFlightDataContainer currentForecast = this.realtimeFlightDataContainer.getValue();
        if (currentForecast == null) {
            return true;
        }

        /*
         * Fetch forecast if there was an error fetching the last one.
         */
        if (this.loadingStatus.getValue() == LoadingStatus.ERROR) {
            return true;
        }

        /*
         * Fetch forecast if either location or units have changed.
         */
        if (!TextUtils.equals(location, this.currentLocation) || !TextUtils.equals(units, this.currentUnits)) {
            return true;
        }

        /*
         * Fetch forecast if the earliest of the current forecast data is timestamped before "now".
         */
//        if (currentForecast.getForecastDataList() != null && currentForecast.getForecastDataList().size() > 0) {
//            ForecastData firstForecastData = currentForecast.getForecastDataList().get(0);
//            if (firstForecastData.getEpoch() * 1000L < System.currentTimeMillis()) {
//                return true;
//            }
//        }

        /*
         * Otherwise, don't fetch the forecast.
         */
        return false;
    }
}

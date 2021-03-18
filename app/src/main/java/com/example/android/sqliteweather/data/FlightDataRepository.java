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
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FlightDataRepository {
    private static final String TAG = FlightDataRepository.class.getSimpleName();
    private static final String BASE_URL = "http://api.aviationstack.com/v1/";
    private static final String API_KEY = "cc6ab53163a7665b76614114d0740210";


    private MutableLiveData<RealtimeFlightDataContainer> realtimeFlightDataContainer;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentDepartureCity;
    private String currentArrivalCity;
    private String currentFlightDate;

    private AviationStackService aviationStackService;

    public FlightDataRepository() {
        this.realtimeFlightDataContainer = new MutableLiveData<>();
        this.realtimeFlightDataContainer.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.aviationStackService = retrofit.create(AviationStackService.class);
    }

    public LiveData<RealtimeFlightDataContainer> getRealtimeFlightDataContainer() {
        return this.realtimeFlightDataContainer;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadFlight(String departureCity, String arrivalCity, String flightDate) {
        if (shouldFetchFlight(departureCity, arrivalCity, flightDate)) {
            Log.d(TAG, "fetching new forecast data for cities: " + arrivalCity + " " + departureCity + ", flightDate: " + flightDate);

            //this.currentLocation = location;
            //this.currentUnits = units;
            this.realtimeFlightDataContainer.setValue(null);
            this.loadingStatus.setValue(LoadingStatus.LOADING);

            Call<String> req = this.aviationStackService.fetchFlight(arrivalCity, departureCity, API_KEY);
            req.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "request URL: " + call.request().url());

                        RealtimeFlightDataContainer dataContainer = new Gson().fromJson(response.body(), RealtimeFlightDataContainer.class);
                        realtimeFlightDataContainer.setValue(dataContainer);

                        loadingStatus.setValue(LoadingStatus.SUCCESS);
                    } else {
                        loadingStatus.setValue(LoadingStatus.ERROR);
                        Log.d(TAG, "unsuccessful API request: " + call.request().url());
                        Log.d(TAG, "  -- response status code: " + response.code());
                        Log.d(TAG, "  -- response: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    t.printStackTrace();
                }
            });
        } else {
            Log.d(TAG, "using cached forecast data for cities: " + arrivalCity + " " + departureCity + ", flightDate: " + flightDate);
        }
    }

    private boolean shouldFetchFlight(String departureCity, String arrivalCity, String flightDate) {
        /*
         * Fetch flight data if there isn't currently one stored.
         */
        RealtimeFlightDataContainer currentForecast = this.realtimeFlightDataContainer.getValue();
        if (currentForecast == null) {
            return true;
        }

        /*
         * Fetch flight data if there was an error fetching the last one.
         */
        if (this.loadingStatus.getValue() == LoadingStatus.ERROR) {
            return true;
        }

        /*
         * Fetch flight data if either location or units have changed.
         */
        if (!TextUtils.equals(departureCity, this.currentDepartureCity) || !TextUtils.equals(arrivalCity, this.currentArrivalCity) || !TextUtils.equals(flightDate, this.currentFlightDate)) {
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
         * Otherwise, don't fetch the flight data.
         */
        return false;
    }
}
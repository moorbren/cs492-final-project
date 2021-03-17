package com.example.android.sqliteweather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.sqliteweather.data.FlightDataRepository;
import com.example.android.sqliteweather.data.LoadingStatus;
import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

public class FlightDataViewModel extends ViewModel {
    private FlightDataRepository repository;
    private LiveData<RealtimeFlightDataContainer> realtimeFlightDataContainer;
    private LiveData<LoadingStatus> loadingStatus;

    public FlightDataViewModel() {
        this.repository = new FlightDataRepository();
        realtimeFlightDataContainer = repository.getRealtimeFlightDataContainer();
        loadingStatus = repository.getLoadingStatus();
    }

    public LiveData<RealtimeFlightDataContainer> getRealtimeFlightDataContainer() {
        return this.realtimeFlightDataContainer;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadFlight(String departureCity, String arrivalCity, String flightDate) {
        this.repository.loadFlight(departureCity, arrivalCity, flightDate);
    }
}

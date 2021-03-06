package com.example.android.sqliteweather;


import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;
import com.example.android.sqliteweather.data.FlightDataRepository;
import com.example.android.sqliteweather.data.LoadingStatus;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FlightDataViewModel extends ViewModel {
    private FlightDataRepository repository;
    private LiveData<RealtimeFlightDataContainer> flightData;

    private LiveData<LoadingStatus> loadingStatus;

    public FlightDataViewModel() {
        this.repository = new FlightDataRepository();
        flightData = repository.getRealtimeFlightDataContainer();
        loadingStatus = repository.getLoadingStatus();
    }

    public LiveData<RealtimeFlightDataContainer> getRealtimeFlightDataContainer() {
        return this.flightData;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadFlight(String departureCity, String arrivalCity, String flightDate) {
        this.repository.loadFlight(departureCity, arrivalCity, flightDate); // Check how to search for flights
    }
}

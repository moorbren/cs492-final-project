package com.example.android.sqliteweather.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoritedFlightsViewModel extends AndroidViewModel {
    private FavoritedFlightsRepository repository;

    public FavoritedFlightsViewModel (Application application) {
        super (application);
        this.repository = new FavoritedFlightsRepository(application);
    }

    public void insertFavoritedFlight (FavoritedFlights flight) { this.repository.insertFavoriteFlight(flight);}
    public void deleteFavoritedFlight (FavoritedFlights flight) { this.repository.deleteFavoriteFlight(flight);}
    public LiveData<List<FavoritedFlights>> getAllFavorites () { return this.repository.getAllFavorites(); }
}

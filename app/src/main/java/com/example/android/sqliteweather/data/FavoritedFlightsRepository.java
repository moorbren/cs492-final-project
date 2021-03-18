package com.example.android.sqliteweather.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoritedFlightsRepository {

    private FlightsDao dao;

    public FavoritedFlightsRepository (Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.flightsDao();
    }

    public void insertFavoriteFlight (FavoritedFlights search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(search);
            }
        });
    }

    public void deleteFavoriteFlight (FavoritedFlights search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(search);
            }
        });
    }

    public LiveData<List<FavoritedFlights>> getAllFavorites () {
        return this.dao.getAllFavorites();
    }

}

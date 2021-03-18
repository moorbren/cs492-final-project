package com.example.android.sqliteweather.data;

import android.app.Application;

public class FavoritedFlightsRepository {

    private FlightsDao dao;

    public FavoritedFlightsRepository (Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.flightsDao();
    }

    public void insertCitySearch (FavoritedFlights search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(search);
            }
        });
    }

    public void deleteCitySearch (FavoritedFlights search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(search);
            }
        });
    }
}

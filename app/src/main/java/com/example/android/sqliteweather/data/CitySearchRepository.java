package com.example.android.sqliteweather.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CitySearchRepository {
    private CitiesDao dao;

    public CitySearchRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.citiesDao();
    }

    public void insertCitySearch (CitySearch search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(search);
            }
        });
    }

    public void deleteCitySearch (CitySearch search) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(search);
            }
        });
    }

    public LiveData<List<CitySearch>> getAllCities () {
        return this.dao.getAllCities();
    }
}

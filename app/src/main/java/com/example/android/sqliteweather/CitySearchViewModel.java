package com.example.android.sqliteweather;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.sqliteweather.data.CitySearch;
import com.example.android.sqliteweather.data.CitySearchRepository;

import java.util.List;

public class CitySearchViewModel extends AndroidViewModel {
    private CitySearchRepository repository;

    public CitySearchViewModel(Application application) {
        super(application);
        this.repository = new CitySearchRepository(application);
    }

    public void insertSearchCity(CitySearch search) { this.repository.insertCitySearch(search); }
    public void deleteSearchCity(CitySearch search) {
        this.repository.deleteCitySearch(search);
    }
    public LiveData<List<CitySearch>> getAllCities () { return this.repository.getAllCities(); }
}

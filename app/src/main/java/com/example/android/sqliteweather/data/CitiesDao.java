package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (CitySearch city);

    @Delete
    void delete(CitySearch city);

    @Query("SELECT * FROM cities")
    LiveData<List<CitySearch>> getAllCities();
}

package com.example.android.sqliteweather.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightsDao {
    @Insert
    void insert (FavoritedFlights flight);

    @Delete
    void delete (FavoritedFlights flight);

    @Query("SELECT * FROM cities")
    LiveData<List<FavoritedFlights>> getAllFavorites();
}

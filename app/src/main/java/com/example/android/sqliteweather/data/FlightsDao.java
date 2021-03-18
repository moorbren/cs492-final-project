package com.example.android.sqliteweather.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface FlightsDao {
    @Insert
    void insert (FavoritedFlights flight);

    @Delete
    void delete (FavoritedFlights flight);
}

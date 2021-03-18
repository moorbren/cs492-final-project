package com.example.android.sqliteweather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "flights")
public class FavoritedFlights implements Serializable {
    @SerializedName("departureNum")
    @PrimaryKey
    @NonNull
    public String departureNum;

    public String airline;

    public String departure;

    public String arrival;

    public String flightNum;



    public FavoritedFlights (String departureNum, String airline, String departure, String arrival, String flightNum) {
        this.departureNum=departureNum;
        this.airline=airline;
        this.departure=departure;
        this.arrival=arrival;
        this.flightNum=flightNum;
    }

}

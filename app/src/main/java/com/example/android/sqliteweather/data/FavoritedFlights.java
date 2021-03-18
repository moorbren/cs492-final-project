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

//    public String airline;
//
//    public String departure;
//
//    public String arrival;
//
//    public String flightNumber;



    public FavoritedFlights (String departureNum) {
        this.departureNum=departureNum;
    }

}

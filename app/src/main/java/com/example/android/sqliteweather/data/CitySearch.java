package com.example.android.sqliteweather.data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "cities")
public class CitySearch implements Serializable {
    @SerializedName("city")
    @PrimaryKey
    @NonNull
    public String city;

    @SerializedName("date")
    public String date;
}

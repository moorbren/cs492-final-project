package com.example.android.sqliteweather.data;

import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("forecast")
    Call<RealtimeFlightDataContainer> fetchFlight(
            @Query("q") String location,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}

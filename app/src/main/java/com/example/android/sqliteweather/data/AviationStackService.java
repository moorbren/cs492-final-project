package com.example.android.sqliteweather.data;

import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AviationStackService {
    @GET("flights")
    Call<String> fetchFlight(
            @Query("arr_iata") String arrivalIata,
            @Query("dep_iata") String departureIata,
            @Query("access_key") String apiKey
    );
}

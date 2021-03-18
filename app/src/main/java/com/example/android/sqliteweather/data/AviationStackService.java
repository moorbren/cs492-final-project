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

            //YYYY-MM-DD, EX: 2019-02-31
            //@Query("flight_date") String destination,
            @Query("access_key") String apiKey
    );
}

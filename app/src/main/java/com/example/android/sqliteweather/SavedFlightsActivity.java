package com.example.android.sqliteweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.sqliteweather.data.FavoritedFlights;
import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;
import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer.RealtimeFlightData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SavedFlightsActivity extends AppCompatActivity implements FlightDataAdapter.OnFlightItemClickListener{

    public static final String EXTRA_FLIGHT_DATA = "FavoritedFlights";

    private FlightDataAdapter flightDataAdapter;
    private List<FavoritedFlights> favoritedFlights;

    private RecyclerView flightListRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.saved_flights_activity); //sets layout to specified XML


        this.flightListRv = findViewById(R.id.rv_saved_flights);
        this.flightListRv.setLayoutManager(new LinearLayoutManager(this));
        this.flightListRv.setHasFixedSize(true);

        this.flightDataAdapter = new FlightDataAdapter(this);
        this.flightListRv.setAdapter(this.flightDataAdapter);
        this.flightListRv.setItemAnimator(new DefaultItemAnimator());


        Intent intent = getIntent();
        if(intent != null){
            String flights = intent.getStringExtra(EXTRA_FLIGHT_DATA);

            Type listType = new TypeToken<List<FavoritedFlights>>(){}.getType();
            List<FavoritedFlights> favoritedFlights = new Gson().fromJson(flights, listType);

            RealtimeFlightDataContainer realtimeFlightDataContainer = new RealtimeFlightDataContainer(favoritedFlights);
            this.flightDataAdapter.updateFlightData(realtimeFlightDataContainer);
        }
    }


    @Override
    public void onFlightItemClick(RealtimeFlightData flightData) {
        Intent intent = new Intent(this, FlightDetailActivity.class);
        intent.putExtra(FlightDetailActivity.EXTRA_FLIGHT_DATA, flightData);

        startActivity(intent);
    }
}

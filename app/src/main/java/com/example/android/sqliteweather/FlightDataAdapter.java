package com.example.android.sqliteweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

public class FlightDataAdapter extends RecyclerView.Adapter<FlightDataAdapter.FlightItemViewHolder> {
    private RealtimeFlightDataContainer flightData;
    private OnFlightItemClickListener onFlightItemClickListener;

    public interface OnFlightItemClickListener {
        void onFlightItemClick(RealtimeFlightDataContainer.RealtimeFlightData flightData);
    }


    public FlightDataAdapter(OnFlightItemClickListener onFlightItemClickListener) {
        this.onFlightItemClickListener = onFlightItemClickListener;
    }

    @NonNull
    @Override
    public FlightItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.flightdata_list_item, parent, false);
        return new FlightItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightDataAdapter.FlightItemViewHolder holder, int position) {
        holder.bind(this.flightData.data[position]);
    }

    @Override
    public int getItemCount() {
        if(this.flightData != null && this.flightData.data != null){
            return this.flightData.data.length;
        }

        Log.w("FlightDataAdapter", "Flight data adapter has no items set!");

        return 0;
    }

    public void updateFlightData(RealtimeFlightDataContainer flightData) {
        Log.d("FlightDataAdapter", flightData.toString());
        //Log.d("FlightDataAdapter", flightData.data[0].aircraft.icao);
        this.flightData = flightData;
        notifyDataSetChanged();
    }

    class FlightItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView departureTV;
        final private TextView arrivalTV;
        final private TextView flightNumTV;

        public FlightItemViewHolder(@NonNull View itemView) {
            super(itemView);
            departureTV = itemView.findViewById(R.id.tv_departure_city);
            arrivalTV = itemView.findViewById(R.id.tv_arrival_city);
            flightNumTV = itemView.findViewById(R.id.tv_flight_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFlightItemClickListener.onFlightItemClick(
                            flightData.data[getAdapterPosition()]
                    );
                }
            });
        }

        public void bind(RealtimeFlightDataContainer.RealtimeFlightData flightData) {
            Log.d("BIND FUNCTION", flightData.departure.airport);

            departureTV.setText(flightData.departure.airport);
            arrivalTV.setText(flightData.arrival.airport);
            flightNumTV.setText(flightData.flight.number);
        }

    }
}

package com.example.android.sqliteweather;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.sqliteweather.data.json.RealtimeFlightDataContainer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.O)
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

        return 0;
    }

    public void updateFlightData(RealtimeFlightDataContainer flightData) {
        this.flightData = flightData;
        if(flightData != null && flightData.data != null) {
            ArrayList<RealtimeFlightDataContainer.RealtimeFlightData> sorted = new ArrayList<>(Arrays.asList(flightData.data));
            sorted.sort((o1, o2) -> o1.departure.scheduled.compareTo(o2.departure.scheduled));
            this.flightData.data = sorted.toArray(new RealtimeFlightDataContainer.RealtimeFlightData[sorted.size()]);
        }

        notifyDataSetChanged();
    }

    class FlightItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView departureIataTV;
        final private TextView departureDateTV;
        final private TextView arrivalIataTV;
        final private TextView flightNumTV;
        final private TextView flightAirlineTV;

        public FlightItemViewHolder(@NonNull View itemView) {
            super(itemView);
            departureDateTV = itemView.findViewById(R.id.tv_departure_date);
            departureIataTV = itemView.findViewById(R.id.tv_departure_iata);
            arrivalIataTV = itemView.findViewById(R.id.tv_arrival_iata);
            flightNumTV = itemView.findViewById(R.id.tv_flight_number);
            flightAirlineTV = itemView.findViewById(R.id.tv_flight_airline);

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
            String s = LocalDateTime.parse(
                    flightData.departure.getScheduled(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ).format(
                    DateTimeFormatter.ofPattern("EEE, MMM d @ h:mm a")
            );

            departureDateTV.setText(s);
            departureIataTV.setText(flightData.departure.iata);
            arrivalIataTV.setText(flightData.arrival.iata);
            flightNumTV.setText("#" + flightData.flight.number);
            flightAirlineTV.setText(flightData.airline.name);
        }
    }
}

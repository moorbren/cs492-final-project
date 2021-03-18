package com.example.android.sqliteweather.data.json;

import com.example.android.sqliteweather.data.FavoritedFlights;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RealtimeFlightDataContainer implements Serializable {

    public RealtimeFlightData[] data;

    public RealtimeFlightDataContainer(List<FavoritedFlights> flights){
        data = new RealtimeFlightData[flights.size()];
        for(int i = 0; i < data.length; i++){
            FavoritedFlights f = flights.get(i);

            String[] s = f.departureNum.replace(" | ", ",").split(",");
            String departDate = s[0];
            String flightNummm = s[1];


            RealtimeFlightData convertedFlight = new RealtimeFlightData(departDate, f.airline, f.departure, f.arrival, flightNummm);
            data[i] = convertedFlight;
        }
    }

    public class RealtimeFlightData implements Serializable {
        public Departure departure;
        public Arrival arrival;
        public Airline airline;
        public Flight flight;
        public Aircraft aircraft;
        public Live live;

        public RealtimeFlightData(String departureNum, String airline, String departure, String arrival, String flightNum){
            this.departure = new Departure(departure, departureNum);
            this.arrival = new Arrival(arrival);
            this.airline = new Airline(airline);
            this.flight = new Flight(flightNum);
        }

        public class Departure implements Serializable {
            public String airport;
            public String timezone;
            public String iata;
            public String icao;
            public String terminal;
            public String gate;
            public int delay;
            public String scheduled;
            public String estimated;
            public String actual;
            public String estimated_runway;

            public Departure(String airport, String timezone, String iata, String icao, String terminal, String gate, int delay, String scheduled, String estimated, String actual, String estimated_runway) {
                this.airport = airport;
                this.timezone = timezone;
                this.iata = iata;
                this.icao = icao;
                this.terminal = terminal;
                this.gate = gate;
                this.delay = delay;
                this.scheduled = scheduled;
                this.estimated = estimated;
                this.actual = actual;
                this.estimated_runway = estimated_runway;

            }

            public Departure(String iata, String scheduled){
                this.iata = iata;
                this.scheduled = scheduled;
            }

            public String getAirport() {
                return airport;
            }

            public void setAirport(String airport) {
                this.airport = airport;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getIcao() {
                return icao;
            }

            public void setIcao(String icao) {
                this.icao = icao;
            }

            public String getTerminal() {
                return terminal;
            }

            public void setTerminal(String terminal) {
                this.terminal = terminal;
            }

            public String getGate() {
                return gate;
            }

            public void setGate(String gate) {
                this.gate = gate;
            }

            public int getDelay() {
                return delay;
            }

            public void setDelay(int delay) {
                this.delay = delay;
            }

            public String getScheduled() {
                return scheduled;
            }

            public void setScheduled(String scheduled) {
                this.scheduled = scheduled;
            }

            public String getEstimated() {
                return estimated;
            }

            public void setEstimated(String estimated) {
                this.estimated = estimated;
            }

            public String getActual() {
                return actual;
            }

            public void setActual(String actual) {
                this.actual = actual;
            }

            public String getEstimated_runway() {
                return estimated_runway;
            }

            public void setEstimated_runway(String estimated_runway) {
                this.estimated_runway = estimated_runway;
            }
        }

        public class Arrival implements Serializable {
            public String airport;
            public String timezone;
            public String iata;
            public String icao;
            public String terminal;
            public String gate;
            public String baggage;
            public int delay;
            public String scheduled;
            public String estimated;
            public String actual;
            public String estimated_runway;

            public Arrival(String airport, String timezone, String iata, String icao, String terminal, String gate, String baggage, int delay, String scheduled, String estimated, String actual, String estimated_runway) {
                this.airport = airport;
                this.timezone = timezone;
                this.iata = iata;
                this.icao = icao;
                this.terminal = terminal;
                this.gate = gate;
                this.baggage = baggage;
                this.delay = delay;
                this.scheduled = scheduled;
                this.estimated = estimated;
                this.actual = actual;
                this.estimated_runway = estimated_runway;
            }

            public Arrival(String iata) {
                this.iata = iata;
            }

            public String getAirport() {
                return airport;
            }

            public void setAirport(String airport) {
                this.airport = airport;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getIcao() {
                return icao;
            }

            public void setIcao(String icao) {
                this.icao = icao;
            }

            public String getTerminal() {
                return terminal;
            }

            public void setTerminal(String terminal) {
                this.terminal = terminal;
            }

            public String getGate() {
                return gate;
            }

            public void setGate(String gate) {
                this.gate = gate;
            }

            public String getBaggage() {
                return baggage;
            }

            public void setBaggage(String baggage) {
                this.baggage = baggage;
            }

            public int getDelay() {
                return delay;
            }

            public void setDelay(int delay) {
                this.delay = delay;
            }

            public String getScheduled() {
                return scheduled;
            }

            public void setScheduled(String scheduled) {
                this.scheduled = scheduled;
            }

            public String getEstimated() {
                return estimated;
            }

            public void setEstimated(String estimated) {
                this.estimated = estimated;
            }

            public String getActual() {
                return actual;
            }

            public void setActual(String actual) {
                this.actual = actual;
            }

            public String getEstimated_runway() {
                return estimated_runway;
            }

            public void setEstimated_runway(String estimated_runway) {
                this.estimated_runway = estimated_runway;
            }
        }

        public class Airline implements Serializable {
            public String name;
            public String iata;
            public String icao;

            public Airline(String name, String iata, String icao) {
                this.name = name;
                this.iata = iata;
                this.icao = icao;
            }

            public Airline(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getIcao() {
                return icao;
            }

            public void setIcao(String icao) {
                this.icao = icao;
            }
        }

        public class Flight implements Serializable {
            public String number;
            public String iata;
            public String icao;

            public Flight(String number, String iata, String icao) {
                this.number = number;
                this.iata = iata;
                this.icao = icao;
            }

            public Flight(String number) {
                this.number = number;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getIcao() {
                return icao;
            }

            public void setIcao(String icao) {
                this.icao = icao;
            }


        }

        public class Aircraft implements Serializable {
            public String registration;
            public String iata;
            public String icao;
            public String icao24;

            public Aircraft(String registration, String iata, String icao, String icao24) {
                this.registration = registration;
                this.iata = iata;
                this.icao = icao;
                this.icao24 = icao24;
            }

            public String getRegistration() {
                return registration;
            }

            public void setRegistration(String registration) {
                this.registration = registration;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getIcao() {
                return icao;
            }

            public void setIcao(String icao) {
                this.icao = icao;
            }

            public String getIcao24() {
                return icao24;
            }

            public void setIcao24(String icao24) {
                this.icao24 = icao24;
            }
        }

        public class Live implements Serializable {
            public String updated;
            public float latitude;
            public float longitude;
            public float altitude;
            public float direction;
            public float speed_horizontal;
            public float speed_vertical;
            public boolean is_ground;

            public Live(String updated, float latitude, float longitude, float altitude, float direction, float speed_horizontal, float speed_vertical, boolean is_ground) {
                this.updated = updated;
                this.latitude = latitude;
                this.longitude = longitude;
                this.altitude = altitude;
                this.direction = direction;
                this.speed_horizontal = speed_horizontal;
                this.speed_vertical = speed_vertical;
                this.is_ground = is_ground;
            }

            public String getUpdated() {
                return updated;
            }

            public void setUpdated(String updated) {
                this.updated = updated;
            }

            public float getLatitude() {
                return latitude;
            }

            public void setLatitude(float latitude) {
                this.latitude = latitude;
            }

            public float getLongitude() {
                return longitude;
            }

            public void setLongitude(float longitude) {
                this.longitude = longitude;
            }

            public float getAltitude() {
                return altitude;
            }

            public void setAltitude(float altitude) {
                this.altitude = altitude;
            }

            public float getDirection() {
                return direction;
            }

            public void setDirection(float direction) {
                this.direction = direction;
            }

            public float getSpeed_horizontal() {
                return speed_horizontal;
            }

            public void setSpeed_horizontal(float speed_horizontal) {
                this.speed_horizontal = speed_horizontal;
            }

            public float getSpeed_vertical() {
                return speed_vertical;
            }

            public void setSpeed_vertical(float speed_vertical) {
                this.speed_vertical = speed_vertical;
            }

            public boolean isIs_ground() {
                return is_ground;
            }

            public void setIs_ground(boolean is_ground) {
                this.is_ground = is_ground;
            }
        }
    }

}

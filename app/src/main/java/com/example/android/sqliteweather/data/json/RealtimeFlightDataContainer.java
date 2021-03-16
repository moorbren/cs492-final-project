package com.example.android.sqliteweather.data.json;

import java.io.Serializable;


public class RealtimeFlightDataContainer implements Serializable {

    public RealtimeFlightData[] data;

    public class RealtimeFlightData implements Serializable {
        public Departure departure;
        public Arrival arrival;
        public Airline airline;
        public Flight flight;
        public Aircraft aircraft;
        public Live live;

        class Departure implements Serializable {
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
        }

        class Arrival implements Serializable {
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
        }

        class Airline implements Serializable {
            public String name;
            public String iata;
            public String icao;

            public Airline(String name, String iata, String icao) {
                this.name = name;
                this.iata = iata;
                this.icao = icao;
            }
        }

        class Flight implements Serializable {
            public String number;
            public String iata;
            public String icao;
            public String codeshared;

            public Flight(String number, String iata, String icao, String codeshared) {
                this.number = number;
                this.iata = iata;
                this.icao = icao;
                this.codeshared = codeshared;
            }
        }

        class Aircraft implements Serializable {
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
        }

        class Live implements Serializable {
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
        }
    }

}

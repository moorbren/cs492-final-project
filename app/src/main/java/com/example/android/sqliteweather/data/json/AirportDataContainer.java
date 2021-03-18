package com.example.android.sqliteweather.data.json;

import java.io.Serializable;
import java.util.Map;

public class AirportDataContainer implements Serializable {

    public Map<String, AirportData> icaoLookup;

    public class AirportData implements Serializable {
        public String icao;
        public String iata;
        public String name;
        public String city;
        public String state;
        public String country;
        public String tz;

        public AirportData(String icao, String iata, String name, String city, String state, String country, String tz) {
            this.icao = icao;
            this.iata = iata;
            this.name = name;
            this.city = city;
            this.state = state;
            this.country = country;
            this.tz = tz;
        }
    }

    public AirportDataContainer(Map<String, AirportData> icaoLookup) {
        this.icaoLookup = icaoLookup;
    }
}

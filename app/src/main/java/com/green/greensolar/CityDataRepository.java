package com.green.greensolar;

import com.green.greensolar.Data.CityData;

import java.util.Arrays;
import java.util.List;

public class CityDataRepository {
    static private final List<CityData> CITIES_DATA = Arrays.asList(
            new CityData("Juiz de Fora",4.72,4.52),
            new CityData("Carangola",5.055,4.84),
            new CityData("São João Nepomuceno",4.89,4.70),
            new CityData("Bicas",4.77,4.60),
            new CityData("Ponte Nova",4.97,4.78));

    public final List<CityData> getCitiesList() {
        return CITIES_DATA;
    }
}

package com.green.greensolar.Controllers;

import androidx.annotation.NonNull;

import com.green.greensolar.Data.CityData;
import com.green.greensolar.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainViewDataController {
    static public final List<CityData> mCitiesData = Arrays.asList(
            new CityData("Juiz de Fora",4.72,4.52),
            new CityData("Carangola",5.055,4.84),
            new CityData("São João Nepomuceno",4.89,4.70),
            new CityData("Bicas",4.77,4.60),
            new CityData("Ponte Nova",4.97,4.78));
    static private final double defaultFare = 0.75;

    public MainViewDataController() { }

    public List<String> getListOfCityNames() {
        List<String> citiesNames = new ArrayList<String>();
        for (CityData city: mCitiesData) {
            citiesNames.add(city.getName());
        }
        return citiesNames;
    }

    public CityData getCityDataByName(String cityName) {
        for(CityData city : mCitiesData) {
            if(city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public boolean validateEnergyConsumption(@NonNull String energyConsumption) {
        return !energyConsumption.isEmpty() && Double.parseDouble(energyConsumption) != 0;
    }

    public double validateFare(String fare) {
        if(fare.isEmpty() || Double.parseDouble(fare) == 0) {
            return defaultFare;
        }

        return Double.parseDouble(fare);
    }

    public int getNumberOfPhases(int id) {
        switch(id) {
            case R.id.mono_option:
                return 1;
            case R.id.bi_option:
                return 2;
            case R.id.tri_option:
                return 3;

            default:
                return -1;
        }
    }
}

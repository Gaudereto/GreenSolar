package com.green.greensolar.PvSystemInput;

import com.green.greensolar.Data.SolarSystem;

import java.util.List;

public interface PvSystemInputContract {

    interface View {
        void showAvaiableCitiesName(List<String> cities);
        void showCityNotFoundError();
        void showInvalidEnergyConsumption();
        void showInvalidNumberOfPhases();
    }

    interface Presenter {
        void fetchCityData();
        SolarSystem buildSolarSystem(String city,
                                     String Consumption,
                                     String fare,
                                     int phases);
    }
}

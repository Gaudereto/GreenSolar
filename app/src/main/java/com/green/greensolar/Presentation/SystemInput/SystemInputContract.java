package com.green.greensolar.Presentation.SystemInput;

import com.green.greensolar.Data.SolarSystem;
import java.util.List;

public interface SystemInputContract {

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

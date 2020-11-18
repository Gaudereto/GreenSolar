package com.green.greensolar.Presentation.SystemInput;

import androidx.annotation.NonNull;

import com.green.greensolar.CityDataRepository;
import com.green.greensolar.Data.CityData;
import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.R;
import java.util.ArrayList;
import java.util.List;

public class SystemInputPresenter implements SystemInputContract.Presenter {
    private final SystemInputContract.View mView;

    static private final double DEFAULT_FARE = 0.75;
    private final CityDataRepository repository;

    public SystemInputPresenter(SystemInputContract.View view) {
        repository = new CityDataRepository();
        mView = view;
    }

    public List<String> getListOfCityNames() {
        List<String> citiesNames = new ArrayList<>();
        for (CityData city: repository.getCitiesList()) {
            citiesNames.add(city.getName());
        }
        return citiesNames;
    }

    public CityData getCityDataByName(String cityName) {
        for(CityData city : repository.getCitiesList()) {
            if(city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public Double validateEnergyConsumption(@NonNull String energyConsumption) {
        if(energyConsumption.isEmpty()) {
            return null;
        }

        double consumption = Double.parseDouble(energyConsumption);
        if(consumption == 0) {
            return null;
        }
        return consumption;
    }

    public double validateFare(String fare) {
        if(fare.isEmpty()) {
            return DEFAULT_FARE;
        }

        double fareValue = Double.parseDouble(fare);
        if(fareValue == 0) {
            return DEFAULT_FARE;
        }
        return fareValue;
    }

    public Integer getNumberOfPhases(int id) {
        switch(id) {
            case R.id.mono_option:
                return 1;
            case R.id.bi_option:
                return 2;
            case R.id.tri_option:
                return 3;
            default:
                return null;
        }
    }

    @Override
    public void fetchCityData() {
        mView.showAvaiableCitiesName(getListOfCityNames());
    }

    @Override
    public SolarSystem buildSolarSystem(String city,
                                        String consumption,
                                        String fare,
                                        int phases) {

        CityData chosenCity = getCityDataByName(city);
        if(chosenCity == null) {
            mView.showCityNotFoundError();
            return null;
        }

        Double consumptionValue = validateEnergyConsumption(consumption);
        if(consumptionValue == null){
            mView.showInvalidEnergyConsumption();
            return null;
        }

        double fareValue = validateFare(fare);

        Integer phasesValue = getNumberOfPhases(phases);
        if(phasesValue == null) {
            mView.showInvalidNumberOfPhases();
            return null;
        }

        return new SolarSystem(
                chosenCity,
                consumptionValue,
                fareValue,
                phasesValue);
    }
}

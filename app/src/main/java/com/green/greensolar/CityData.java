package com.green.greensolar;

public class CityData {
    private String mCity;
    private double mIrradiation;

    public CityData(String city, double irradiation) {
        mCity = city;
        mIrradiation = irradiation;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public double getIrradiation() {
        return mIrradiation;
    }

    public void setIrradiation(double irradiation) {
        mIrradiation = irradiation;
    }
}

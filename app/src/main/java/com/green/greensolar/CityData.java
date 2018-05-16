package com.green.greensolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityData implements Serializable {
    private String mCity;
    private double mIrradiationInclined;
    private double mIrradiationHorizontal;


    public CityData(String city, double irradiationInclined, double irradiationHorizontal) {
        mCity = city;
        mIrradiationInclined = irradiationInclined;
        mIrradiationHorizontal = irradiationHorizontal;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public double getIrradiationInclined() {
        return mIrradiationInclined;
    }

    public void setIrradiationInclined(double irradiationInclined) {
        mIrradiationInclined = irradiationInclined;
    }

    public double getIrradiationHorizontal() {
        return mIrradiationHorizontal;
    }

    public void setIrradiationHorizontal(double irradiationHorizontal) {
        mIrradiationHorizontal = irradiationHorizontal;
    }

}

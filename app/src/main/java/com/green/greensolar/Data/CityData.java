package com.green.greensolar.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityData implements Serializable {
    private final String mName;
    private final double mIrradiationInclined;
    private final double mIrradiationHorizontal;

    public CityData(String name, double irradiationInclined, double irradiationHorizontal) {
        mName = name;
        mIrradiationInclined = irradiationInclined;
        mIrradiationHorizontal = irradiationHorizontal;
    }

    public String getName() {
        return mName;
    }

    public double getIrradiationInclined() {
        return mIrradiationInclined;
    }

    public double getIrradiationHorizontal() {
        return mIrradiationHorizontal;
    }


}

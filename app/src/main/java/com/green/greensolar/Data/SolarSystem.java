package com.green.greensolar.Data;

import java.io.Serializable;

public class SolarSystem implements Serializable {
    // Project parameters:
    private static final double PANEL_EFFICIENCY = 0.1697;
    private static final double PERFORMANCE_RATIO = 0.75;
    private static final double PANEL_POWER_PER_AREA = 169.725148;

    // Client input data:
    private final CityData mCity;
    private final double mConsume;
    private final double mFare;
    private final int mSystemPhases;

    // Output system parameters:
    private static class SystemPrice implements Serializable{
        public double min;
        public double max;
    }

    private double mSystemPower;
    private double mSystemArea;
    private final SystemPrice mSystemPrice = new SystemPrice();
    private double mYearEconomy;
    private double mPayback;
    private float mCashFlux[] = new float[25];

    public SolarSystem(CityData city,
                       double consume,
                       double fare,
                       int systemPhases) {
        mCity = city;
        mConsume = consume;
        mFare = fare;
        mSystemPhases = systemPhases;

        generateResults();
    }

    private void generateResults() {
        // Calculate project based in local irradiation:
        double dailyEnergy = PANEL_EFFICIENCY*PERFORMANCE_RATIO*mCity.getIrradiationInclined();
        double monthlyEnergy = dailyEnergy*365/12;
        double projectPowerGeneration = mConsume - getPhaseEnergy(mSystemPhases);
        mSystemArea = projectPowerGeneration/monthlyEnergy;
        mSystemPower = PANEL_POWER_PER_AREA*mSystemArea/1000;

        getPriceByPower(mSystemPower);

        // Economic results:
        mYearEconomy = mSystemArea*PANEL_EFFICIENCY*PERFORMANCE_RATIO*mCity.getIrradiationInclined()*365* mFare;
        mPayback = ((mSystemPrice.min + mSystemPrice.max)/(2*mYearEconomy));
        for (int i=0; i<25; i++){
            mCashFlux[i] = (float) (-(mSystemPrice.min + mSystemPrice.max)/2+mYearEconomy*(i+1));
        }

    }

    public CityData getCityData() {
        return mCity;
    }

    public double getSystemPower() {
        return mSystemPower;
    }

    public double getSystemArea() {
        return mSystemArea;
    }

    public double getMinSystemPrice() {
        return mSystemPrice.min;
    }

    public double getMaxSystemPrice() {
        return mSystemPrice.max;
    }

    public double getYearEconomy() {
        return mYearEconomy;
    }

    public double getPayback() {
        return mPayback;
    }

    public float[] getCashFlux() {
        return mCashFlux;
    }

    private void getPriceByPower(double power) {

        if(power<=3.0){
            mSystemPrice.max = power*1000*7.61;
            mSystemPrice.min = power*1000*6.65;
        } else if ( power>3.0 && power<=6.0) {
            mSystemPrice.max = power*1000*6.22;
            mSystemPrice.min = power*1000*5.44;
        } else if ( power>6.0 && power<=10.0) {
            mSystemPrice.max = power*1000*5.57;
            mSystemPrice.min = power*1000*4.85;
        } else if ( power>10.0 && power<=21.0){
            mSystemPrice.max = power*1000*5.45;
            mSystemPrice.min = power*1000*4.72;
        } else if ( power>21.0 && power<=40.0){
            mSystemPrice.max = power*1000*4.98;
            mSystemPrice.min = power*1000*4.29;
        } else if ( power>40 && power<=62.0) {
            mSystemPrice.max = power*1000*4.79;
            mSystemPrice.min = power*1000*3.99;
        } else if ( power>62 && power<=105.0) {
            mSystemPrice.max = power*1000*4.75;
            mSystemPrice.min = power*1000*3.88;
        } else if ( power>105 && power<=225.0){
            mSystemPrice.max = power*1000*4.62;
            mSystemPrice.min = power*1000*3.64;
        } else if ( power>225 && power<=400) {
            mSystemPrice.max = power*1000*4.60;
            mSystemPrice.min = power*1000*3.44;
        } else if ( power>400) {
            mSystemPrice.max = power*1000*4.58;
            mSystemPrice.min = power*1000*3.39;
        }
    }

    private double getPhaseEnergy(int ph) {
        if(ph==1) {
            return 30.0;
        } else if(ph==2) {
            return 50.0;
        } else if(ph==3) {
            return 100.0;
        }
        return 0.0;
    }
}

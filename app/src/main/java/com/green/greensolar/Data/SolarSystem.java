package com.green.greensolar.Data;

import java.io.Serializable;

public class SolarSystem implements Serializable {
    // Project parameters:
    private static final double PANEL_EFFICIENCY = 0.1697;
    private static final double PERFORMANCE_RATIO = 0.75;
    private static final double PANEL_POWER_PER_AREA = 169.725148;
    public static final int CASH_FLOW_MONTHS = 24;

    // Client input data:
    private final CityData mCity;
    private final double mConsume;
    private final double mFare;
    private final int mSystemPhases;


    private static class SystemPrice implements Serializable{
        public double min;
        public double max;
    }

    // Output system parameters:
    private double mSystemPower;
    private double mSystemArea;
    private final SystemPrice mSystemPrice = new SystemPrice();
    private double mYearEconomy;
    private double mPayback;
    private float mCashFlow[] = new float[CASH_FLOW_MONTHS];

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
        for (int year=0; year < CASH_FLOW_MONTHS; year++){
            mCashFlow[year] = (float) (mYearEconomy*(year+1) -(mSystemPrice.min + mSystemPrice.max)/2);
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

    public float[] getCashFlow() {
        return mCashFlow;
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
        switch(ph) {
            case 1:
                return 30.0;
            case 2:
                return 50.0;
            case 3:
                return 100.0;
            default:
                return 0.0;
        }
    }
}

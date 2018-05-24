package com.green.greensolar;

public class SolarSystem {

    //Dados Cliente:
    private String mCity;
    private double mIrradiationInclined;
    private double mIrradiationHorizontal;
    private double mConsume;
    private double mTarifa;
    private int mSystemPhases;

    //Dados projeto:
    private double mPanelEfficiency;
    private double mPerformanceRatio;

    //Dados sistema:
    private double mSystemPower;
    private double mSystemArea;
    private double mSystemPrices[] = new double[2];
    private double mYearEconomy;
    private double mPayback;



    public SolarSystem(String city, double irradiationInclined, double irradiationHorizontal, double consume, double tarifa, int systemPhases,double panelEfficiency, double performanceRatio) {
        mCity = city;
        mIrradiationInclined = irradiationInclined;
        mIrradiationHorizontal = irradiationHorizontal;
        mConsume = consume;
        mTarifa = tarifa;
        mSystemPhases = systemPhases;
        mPanelEfficiency = panelEfficiency;
        mPerformanceRatio = performanceRatio;

        generateResults();
    }

    private void generateResults(){

        // Projeto baseado na radiação incidente no local de escolha:
        double dailyEnergy = mPanelEfficiency*mPerformanceRatio*mIrradiationInclined;
        double monthlyEnergy = dailyEnergy*365/12;
        double projectConsume = mConsume - getPhaseEnergy(mSystemPhases);
        mSystemArea = projectConsume/monthlyEnergy;
        mSystemPower = 169.725148*mSystemArea/1000;

        getPriceByPower(mSystemPower);

        // Resultados Econômicos do projeto:
        mYearEconomy = mSystemArea*mPanelEfficiency*mPerformanceRatio*mIrradiationInclined*365*mTarifa;
        mPayback = ((mSystemPrices[0] + mSystemPrices[1])/(2*mYearEconomy));
    }

    public double getIrradiationInclined() {
        return mIrradiationInclined;
    }

    public double getSystemPower() {
        return mSystemPower;
    }

    public double getSystemArea() {
        return mSystemArea;
    }

    public double[] getSystemPrices() {
        return mSystemPrices;
    }

    public double getYearEconomy() {
        return mYearEconomy;
    }

    public double getPayback() {
        return mPayback;
    }

    private void getPriceByPower(double power) {

        if(power<=3.0){
            mSystemPrices[0] = power*1000*7.61;
            mSystemPrices[1] = power*1000*6.65;
        } else if ( power>3.0 && power<=6.0) {
            mSystemPrices[0] = power*1000*6.22;
            mSystemPrices[1] = power*1000*5.44;
        } else if ( power>6.0 && power<=10.0) {
            mSystemPrices[0] = power*1000*5.57;
            mSystemPrices[1] = power*1000*4.85;
        } else if ( power>10.0 && power<=21.0){
            mSystemPrices[0] = power*1000*5.45;
            mSystemPrices[1] = power*1000*4.72;
        } else if ( power>21.0 && power<=40.0){
            mSystemPrices[0] = power*1000*4.98;
            mSystemPrices[1] = power*1000*4.29;
        } else if ( power>40 && power<=62.0) {
            mSystemPrices[0] = power*1000*4.79;
            mSystemPrices[1] = power*1000*3.99;
        } else if ( power>62 && power<=105.0) {
            mSystemPrices[0] = power*1000*4.75;
            mSystemPrices[1] = power*1000*3.88;
        } else if ( power>105 && power<=225.0){
            mSystemPrices[0] = power*1000*4.62;
            mSystemPrices[1] = power*1000*3.64;
        } else if ( power>225 && power<=400) {
            mSystemPrices[0] = power*1000*4.60;
            mSystemPrices[1] = power*1000*3.44;
        } else if ( power>400) {
            mSystemPrices[0] = power*1000*4.58;
            mSystemPrices[1] = power*1000*3.39;
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

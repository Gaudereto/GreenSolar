package com.green.greensolar.Presentation.ResultSystem;

import com.github.mikephil.charting.data.LineData;
import com.green.greensolar.Data.SolarSystem;

public interface ResultSystemContract {

    interface View {
        void showIrradiation(String irradiation);
        void showSystemArea(String area);
        void showSystemPower(String power);
        void showSystemPrice(String price);
        void showYearEconomy(String economy);
        void showPayback(String payback);
        void showReturnInTime(LineData data);
    }

    interface Presenter {
        void processResultPvSystem(SolarSystem solarSystem);
    }
}

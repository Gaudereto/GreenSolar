package com.green.greensolar.Presentation.ResultSystem;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultSystemPresenter implements ResultSystemContract.Presenter {
    private final ResultSystemContract.View mView;

    public ResultSystemPresenter(ResultSystemContract.View view) {
        this.mView = view;
    }

    @Override
    public void processResultPvSystem(SolarSystem solarSystem) {
        Locale locale = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

        mView.showIrradiation(
                String.format(
                        locale,
                        "%.1f KWh/m2",
                        solarSystem.getCityData().getIrradiationInclined()));
        mView.showSystemArea(
                String.format(
                        locale,
                        "%.2f m2",
                        solarSystem.getSystemArea()));
        mView.showSystemPower(
                String.format(
                        locale,
                        "%.2f KWp",
                        solarSystem.getSystemPower()));


        String minPrice = nf.format(solarSystem.getMinSystemPrice());
        String maxPrice = nf.format(solarSystem.getMaxSystemPrice());
        mView.showSystemPrice(String.format("De %s até %s", minPrice, maxPrice));

        mView.showYearEconomy(nf.format(solarSystem.getYearEconomy()));

        mView.showPayback(String.format(locale, "%.1f anos", solarSystem.getPayback()));

        // Configuring cash flow:
        float[] cashFlow = solarSystem.getCashFlow();
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i < SolarSystem.CASH_FLOW_MONTHS; i++){
            entries.add(new Entry(i,cashFlow[i]));
        }
        LineDataSet dataSet = new LineDataSet(entries,"Label");
        dataSet.setColor(R.color.light_green);
        mView.showReturnInTime(new LineData(dataSet));
    }
}

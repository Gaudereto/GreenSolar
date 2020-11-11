package com.green.greensolar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

import com.green.greensolar.Data.SolarSystem;

public class ResultsActivity extends AppCompatActivity {

    // Activity member variables:
    private TextView mIrradiationText,
            mSystemPriceText,
            mSystemAreaText,
            mYearEconomyText,
            mSystemPowerText,
            mPaybackText;
    private LineChart mResultsChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // activity views initialization:
        mIrradiationText = findViewById(R.id.irradiation_value_text);
        mSystemPowerText = findViewById(R.id.system_power_value_text);
        mSystemAreaText  = findViewById(R.id.system_area_value_text);
        mSystemPriceText = findViewById(R.id.system_price_value_text);
        mYearEconomyText = findViewById(R.id.year_economy_value_text);
        mPaybackText     = findViewById(R.id.payback_value_text);
        mResultsChart    = findViewById(R.id.results_chart);

        // Get solar system data from client:
        Intent myIntent = getIntent();
        SolarSystem solarSystem = (SolarSystem) myIntent.getSerializableExtra("SolarSystem");

        // Presenting results on the screen:
        showSolarSystemProjectResults(solarSystem);
    }

    void showSolarSystemProjectResults(SolarSystem solarSystem) {
        Locale locale = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

        mIrradiationText.setText(
                String.format(
                        locale,
                        "%.1f KWh/m2",
                        solarSystem.getCityData().getIrradiationInclined()));
        mSystemAreaText.setText(
                String.format(
                        locale,
                        "%.2f m2",
                        solarSystem.getSystemArea()));
        mSystemPowerText.setText(
                String.format(
                        locale,
                        "%.2f KWp",
                        solarSystem.getSystemPower()));


        String minPrice = nf.format(solarSystem.getMinSystemPrice());
        String maxPrice = nf.format(solarSystem.getMaxSystemPrice());
        mSystemPriceText.setText(String.format("De %s até %s", minPrice, maxPrice));
        mYearEconomyText.setText(nf.format(solarSystem.getYearEconomy()));
        mPaybackText.setText(String.format(locale, "%.1f anos", solarSystem.getPayback()));

        // Configuring cash flow
        float[] cashFlow = solarSystem.getCashFlow();
        List<Entry> entries = new ArrayList<>();
        for (float i=0; i < SolarSystem.CASH_FLOW_MONTHS; i++){
            int k=(int)i;
            entries.add(new Entry(i,cashFlow[k]));
        }
        LineDataSet dataSet = new LineDataSet(entries,"Label");
        dataSet.setColor(R.color.light_green);
        LineData lineData = new LineData(dataSet);

        mResultsChart.setData(lineData);
        mResultsChart.getXAxis().setDrawGridLines(false);
        mResultsChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mResultsChart.getAxisRight().setDrawLabels(false);
        mResultsChart.getAxisRight().setDrawAxisLine(true);
        mResultsChart.setMaxVisibleValueCount(4);
        mResultsChart.invalidate();
    }
}

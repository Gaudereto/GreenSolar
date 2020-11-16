package com.green.greensolar.Presentation.ResultSystem;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;

import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.R;

public class ResultSystemActivity extends AppCompatActivity implements ResultSystemContract.View {

    // Activity member variables:
    private ResultSystemContract.Presenter mPresenter;
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
        mPresenter = new ResultSystemPresenter(this);
        mPresenter.processResultPvSystem(solarSystem);
    }

    @Override
    public void showIrradiation(String irradiation) {
        mIrradiationText.setText(irradiation);
    }

    @Override
    public void showSystemArea(String area) {
        mSystemAreaText.setText(area);
    }

    @Override
    public void showSystemPower(String power) {
        mSystemPowerText.setText(power);
    }

    @Override
    public void showSystemPrice(String price) {
        mSystemPriceText.setText(price);
    }

    @Override
    public void showYearEconomy(String economy) {
        mYearEconomyText.setText(economy);
    }

    @Override
    public void showPayback(String payback) {
        mPaybackText.setText(payback);
    }

    @Override
    public void showReturnInTime(LineData data) {
        mResultsChart.setData(data);
        mResultsChart.getXAxis().setDrawGridLines(false);
        mResultsChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mResultsChart.getAxisRight().setDrawLabels(false);
        mResultsChart.getAxisRight().setDrawAxisLine(true);
        mResultsChart.setMaxVisibleValueCount(4);
        mResultsChart.invalidate();
    }
}

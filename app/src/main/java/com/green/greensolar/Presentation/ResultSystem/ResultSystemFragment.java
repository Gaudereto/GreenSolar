package com.green.greensolar.Presentation.ResultSystem;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.R;

public class ResultSystemFragment extends Fragment implements ResultSystemContract.View {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_system, container, false);

        mIrradiationText = view.findViewById(R.id.irradiation_value_text);
        mSystemPowerText = view.findViewById(R.id.system_power_value_text);
        mSystemAreaText  = view.findViewById(R.id.system_area_value_text);
        mSystemPriceText = view.findViewById(R.id.system_price_value_text);
        mYearEconomyText = view.findViewById(R.id.year_economy_value_text);
        mPaybackText     = view.findViewById(R.id.payback_value_text);
        mResultsChart    = view.findViewById(R.id.results_chart);

        // Get solar system data from client:
        SolarSystem solarSystem = (SolarSystem)
                this.getArguments().getSerializable("SolarSystem");;

        // Presenting results on the screen:
        mPresenter = new ResultSystemPresenter(this);
        mPresenter.processResultPvSystem(solarSystem);

        return view;
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

package com.green.greensolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {

    // Activity member variables:
    private TextView mIrradiationText, mSystemPriceText, mSystemAreaText, mYearEconomyText, mSystemPowerText, mPaybackText;
//    private LineChart mResultsChart;
    private CityData mCityData;
    private double mConsumo,mTarifa;
    private int mClientSystemPhases;
    private SolarSystem mSolarSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Inicialização das views da activity:
        mIrradiationText = findViewById(R.id.irradiation_value_text);
        mSystemPowerText = findViewById(R.id.system_power_value_text);
        mSystemAreaText  = findViewById(R.id.system_area_value_text);
        mSystemPriceText = findViewById(R.id.system_price_value_text);
        mYearEconomyText = findViewById(R.id.year_economy_value_text);
        mPaybackText     = findViewById(R.id.payback_value_text);
       // mResultsChart    = findViewById(R.id.results_chart);

        // Realiza a leitura das informações de entrada do cliente:
        Intent myIntent = getIntent();
        mConsumo = Double.parseDouble(myIntent.getStringExtra("Consumo"));
        mTarifa = Double.parseDouble(myIntent.getStringExtra("Tarifa"));
        mCityData = (CityData) myIntent.getSerializableExtra("City");
        mClientSystemPhases = myIntent.getIntExtra("Fases",0);

        // Inicializa um objeto com o projeto do sistema de acordo com os dados do cliente
        mSolarSystem = new SolarSystem(mCityData.getCity(),mCityData.getIrradiationInclined(),mCityData.getIrradiationHorizontal(),mConsumo,mTarifa,mClientSystemPhases,0.1697,0.75);

        // Apresentação dos resultados na tela:
        mIrradiationText.setText(String.format("%.1f",mSolarSystem.getIrradiationInclined())+" KWh/m2");
        mSystemAreaText.setText(String.format("%.2f",mSolarSystem.getSystemArea())+" m2");
        mSystemPowerText.setText(String.format("%.2f",mSolarSystem.getSystemPower())+" KWp");
        double prices[] = mSolarSystem.getSystemPrices();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        String minPrice = nf.format(prices[1]);
        String maxPrice = nf.format(prices[0]);
        mSystemPriceText.setText("De "+minPrice+" até "+maxPrice);
        mYearEconomyText.setText(nf.format(mSolarSystem.getYearEconomy()));
        mPaybackText.setText(String.format("%.1f",mSolarSystem.getPayback())+ " anos");

        /*float cashFlux[] = mSolarSystem.getCashFlux();
        // Configuração do gráfico com resultados:
        List<Entry> entries = new ArrayList<Entry>();
        for (float i=0; i<25; i++){
            int k=(int)i;
            entries.add(new Entry(i,cashFlux[k]));
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
        mResultsChart.invalidate();*/


    }

}

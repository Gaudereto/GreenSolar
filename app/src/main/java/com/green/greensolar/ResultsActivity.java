package com.green.greensolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private TextView testeTextView;
    private CityData city;
    private double myConsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        testeTextView = findViewById(R.id.consumo_teste_text);

        Intent myIntent = getIntent();
        myConsumo = Double.parseDouble(myIntent.getStringExtra("Consumo"));
        city = (CityData) myIntent.getSerializableExtra("City");


        testeTextView.setText(String.valueOf(myConsumo) + " " + city.getCity());

    }
}

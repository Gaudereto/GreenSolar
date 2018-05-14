package com.green.greensolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    TextView testeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent myIntent = getIntent();
        String myConsumo = myIntent.getStringExtra("Consumo");
        testeTextView = findViewById(R.id.consumo_teste_text);

        testeTextView.setText(myConsumo);


    }
}

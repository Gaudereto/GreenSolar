package com.green.greensolar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variaveis constantes:
    private List<CityData> CITIES_DATA = new ArrayList<CityData>();

    // Referências da interface:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView;
    private Button mEstimateButton;
    private String mCitiesName[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mCityTextView = (AutoCompleteTextView)  findViewById(R.id.city_edit_text);
        mConsumeTextView = (EditText) findViewById(R.id.consumo_edit_text);
        mEstimateButton = (Button) findViewById(R.id.estimate_button);

        // Inicia a lista com cidades e respectivas irradiações:
        initCityData();
        // Define o tamanho do vetor que irá receber os nomes das cidades:
        mCitiesName = new String[CITIES_DATA.size()];

        // Vetor com os nomes das cidades:
        for (int i = 0; i < CITIES_DATA.size(); i++) {
            mCitiesName[i] = CITIES_DATA.get(i).getCity();
            Log.d("Cidade","A cidade é: "+  mCitiesName[i]);

        }

        // Configura um adapter para receber as opções de cidade do APP:
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mCitiesName);
        mCityTextView.setAdapter(adapter);
        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityTextView.showDropDown();
            }
        });

        //
        mEstimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choosenCity = mCityTextView.getText().toString();
                boolean teste = CITIES_DATA.contains(choosenCity);

                Log.d("Teste","O teste é: "+  teste);
/*                String newConsumo = mConsumeTextView.getText().toString();
                Intent newIntent = new Intent(MainActivity.this, ResultsActivity.class);
                newIntent.putExtra("Consumo",newConsumo);
                startActivity(newIntent);*/


            }
        });
    }

    void initCityData() {
        CITIES_DATA.add(new CityData("Juiz de Fora",4.72,4.52));
        CITIES_DATA.add(new CityData("Carangola",5.03,4.84));
        CITIES_DATA.add(new CityData("São João Nepomuceno",4.89,4.70));
        CITIES_DATA.add(new CityData("Bicas",4.77,4.60));
        CITIES_DATA.add(new CityData("Ponte Nova",4.97,4.78));
    }

}

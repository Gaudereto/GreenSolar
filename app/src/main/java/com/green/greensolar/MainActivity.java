package com.green.greensolar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

                // Checa se a cidade informada esta no banco de dados e retorna seus dados de irradiação:
                String choosenCity = mCityTextView.getText().toString();
                CityData city = findCityByName(CITIES_DATA,choosenCity);
                if(city==null) {
                    showErrorDialog("Cidade não cadastrada no banco de dados!");
                    return;
                }

                // Checa se o consumo informada é um número válido:
                String newConsumo = mConsumeTextView.getText().toString();
                if(newConsumo.isEmpty()){ return; }
                else { if(Integer.parseInt(newConsumo) == 0 ){return;}}

                // Inicia a pagina para visualizar resultados:
                Intent newIntent = new Intent(MainActivity.this, ResultsActivity.class);
                newIntent.putExtra("Consumo",newConsumo);
                newIntent.putExtra("City",city);
                startActivity(newIntent);


            }
        });
    }

    // Método que inicia uma lista com as cidades cadastradas:
    public void initCityData() {
        CITIES_DATA.add(new CityData("Juiz de Fora",4.72,4.52));
        CITIES_DATA.add(new CityData("Carangola",5.03,4.84));
        CITIES_DATA.add(new CityData("São João Nepomuceno",4.89,4.70));
        CITIES_DATA.add(new CityData("Bicas",4.77,4.60));
        CITIES_DATA.add(new CityData("Ponte Nova",4.97,4.78));
    }

    // Método para procurar a cidade desejada no banco de dados
    private CityData findCityByName(List<CityData> myCityList, String cityName) {
        for(CityData city : myCityList) {
            if(city.getCity().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    // Show error on screen with an alert dialog
    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

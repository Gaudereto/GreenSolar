package com.green.greensolar;

import android.content.Intent;
import android.content.res.Configuration;
import android.drm.DrmStore;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variaveis constantes:
    private final List<CityData> CITIES_DATA = new ArrayList<CityData>();

    // Referências da interface:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView, mClientFareTextView;
    private Button mEstimateButton;
    private String mCitiesName[];
    private RadioGroup mRadioGroup;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização das views da activity:
        mCityTextView = (AutoCompleteTextView)  findViewById(R.id.city_edit_text);
        mConsumeTextView = (EditText) findViewById(R.id.consumo_edit_text);
        mClientFareTextView = (EditText) findViewById(R.id.fare_edit_text);
        mEstimateButton = (Button) findViewById(R.id.estimate_button);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Configurando o navigation drawer:
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToggle.syncState();


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

        // Rotina para o click do botão de estimativa do sistema:
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

                // Checa se a tarifa foi específicada:
                String fare = mClientFareTextView.getText().toString();
                if(newConsumo.isEmpty()){ return; }
                else { if(Integer.parseInt(newConsumo) == 0 ){return;}}
                if(fare.isEmpty()){fare="0.75";}


                //Checa o tipo de entrada de energia:
                int radioID = mRadioGroup.getCheckedRadioButtonId();
                int phases=0;
                switch(radioID) {
                    case R.id.mono_option:
                        phases = 1;
                        break;
                    case R.id.bi_option:
                        phases = 2;
                        break;
                    case R.id.tri_option:
                        phases = 3;
                        break;
                    case -1:
                        showErrorDialog("Por favor informe o tipo do seu padrão de entrada");
                        return;
                }

                // Inicia a pagina para visualizar resultados:
                Intent newIntent = new Intent(MainActivity.this, ResultsActivity.class);
                newIntent.putExtra("Consumo",newConsumo);
                newIntent.putExtra("Fases",phases);
                newIntent.putExtra("Tarifa",fare);
                newIntent.putExtra("City",city);
                startActivity(newIntent);

            }
        });
    }

    // Método que inicia uma lista com as cidades cadastradas:
    private void initCityData() {
        CITIES_DATA.add(new CityData("Juiz de Fora",4.72,4.52));
        CITIES_DATA.add(new CityData("Carangola",5.055,4.84));
        CITIES_DATA.add(new CityData("São João Nepomuceno",4.89,4.70));
        CITIES_DATA.add(new CityData("Bicas",4.77,4.60));
        CITIES_DATA.add(new CityData("Ponte Nova",4.97,4.78));
    }

    // Método para procurar a cidade desejada no banco de dados:
    private CityData findCityByName(List<CityData> myCityList, String cityName) {
        for(CityData city : myCityList) {
            if(city.getCity().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    // Show error on screen with an alert dialog:
    public void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen((GravityCompat.START))){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

}

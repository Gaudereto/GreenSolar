package com.green.greensolar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Referências da interface:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView;
    private Button mEstimateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityTextView = (AutoCompleteTextView)  findViewById(R.id.city_edit_text);
        mConsumeTextView = (EditText) findViewById(R.id.consumo_edit_text);
        mEstimateButton = (Button) findViewById(R.id.estimate_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CITIES);
        mCityTextView.setAdapter(adapter);

    }


    private static final String[] CITIES = new String[] {
            "Juiz de Fora","Belo Horizonte","Rio de Janeiro","São Paulo",
            "Curitiba","Porto Alegre","Vitória","Salvador"
    };
}

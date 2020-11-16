package com.green.greensolar.PvSystemInput;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.R;
import com.green.greensolar.ResultsActivity;

import java.util.List;

public class PvSystemInputFragment extends Fragment implements PvSystemInputContract.View {

    // UI interface refs:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView, mClientFareTextView;
    private RadioGroup mRadioGroup;

    // Main Activity data and logic controller:
    private PvSystemInputContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_bar_main, container, false);

        mPresenter = new PvSystemInputPresenter(this);

        // Activity views initialization:
        mCityTextView = view.findViewById(R.id.city_edit_text);
        mConsumeTextView = view.findViewById(R.id.consumo_edit_text);
        mClientFareTextView = view.findViewById(R.id.fare_edit_text);
        mRadioGroup = view.findViewById(R.id.radio_group);

        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityTextView.showDropDown();
            }
        });

        // Estimate pv system listener button:
        view.findViewById(R.id.estimate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePvSystem();
            }
        });

        mPresenter.fetchCityData();

        // Inflate the layout for this fragment
        return view;
    }

    private void calculatePvSystem() {
        SolarSystem solarSystem = mPresenter.buildSolarSystem(
                mCityTextView.getText().toString(),
                mConsumeTextView.getText().toString(),
                mClientFareTextView.getText().toString(),
                mRadioGroup.getCheckedRadioButtonId());

        if(solarSystem == null) {
            return;
        }

        // Start Solar system result activity:
        Intent newIntent = new Intent(this.getContext(), ResultsActivity.class);
        newIntent.putExtra("SolarSystem", solarSystem);
        startActivity(newIntent);
    }

    @Override
    public void showAvaiableCitiesName(List<String> cities) {
        // City name auto complete adapter config:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_dropdown_item_1line,
                cities);
        mCityTextView.setAdapter(adapter);
    }

    @Override
    public void showCityNotFoundError() {
        showErrorDialog(getString(R.string.error_city_not_found));
    }

    @Override
    public void showInvalidEnergyConsumption() {
        showErrorDialog(getString(R.string.error_invalid_energy_consumption));
    }

    @Override
    public void showInvalidNumberOfPhases() {
        showErrorDialog(getString(R.string.error_envalid_energy_phases));
    }

    // Show error on screen with an alert dialog:
    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

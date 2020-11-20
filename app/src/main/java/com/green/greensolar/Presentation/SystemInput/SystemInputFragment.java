package com.green.greensolar.Presentation.SystemInput;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.green.greensolar.Data.SolarSystem;
import com.green.greensolar.Presentation.ResultSystem.ResultSystemFragment;
import com.green.greensolar.R;

import java.util.List;

public class SystemInputFragment extends Fragment implements SystemInputContract.View {

    // UI interface refs:
    private AutoCompleteTextView mCityTextView;
    private EditText mConsumeTextView, mClientFareTextView;
    private RadioGroup mRadioGroup;

    // Main Activity data and logic controller:
    private SystemInputContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_input, container, false);

        mPresenter = new SystemInputPresenter(this);

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

        // Hide any open keyboard:
        hideKeyboard();

        SystemInputFragmentDirections.ActionResultsFromInput action =
                SystemInputFragmentDirections
                        .actionResultsFromInput(solarSystem);
        Navigation.findNavController(this.getView()).navigate(action);
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

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
    }
}

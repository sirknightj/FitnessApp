package com.example.fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.models.FitnessActivity;
import com.google.android.material.slider.Slider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditFitnessActivityActivity extends AppCompatActivity {

    private EditText input_title, input_description, input_duration, input_calories;
    private CheckBox check_auto_fill_title, check_manual_end_time, check_manual_calories;
    private Slider input_intensity_slider;
    private Map<String, Double> metData;
    private Spinner input_fitness_activity_spinner;
    private FitnessActivity selectedFitnessActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fitness_activity);

        input_title = findViewById(R.id.input_title);
        input_description = findViewById(R.id.input_description);
        input_duration = findViewById(R.id.input_duration);
        input_calories = findViewById(R.id.input_calories);

        check_auto_fill_title = findViewById(R.id.check_auto_fill_title);
        check_manual_end_time = findViewById(R.id.check_manual_end_time);
        check_manual_calories = findViewById(R.id.check_manual_calories);

        input_intensity_slider = findViewById(R.id.input_intensity_slider);
        input_fitness_activity_spinner = findViewById(R.id.input_fitness_activity_spinner);
        input_intensity_slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {

            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                if (slider.getValue() < 10) {
                    slider.setValue(10);
                }
            }
        });

        input_intensity_slider.setLabelFormatter(new Slider.LabelFormatter() {

            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return Math.round(value) + "%";
            }
        });

        // Handles spinner dropdown menu choices
        List<String> inputChoices = new ArrayList<>(FitActivityData.getMetData().keySet());
        Collections.sort(inputChoices);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, inputChoices);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_fitness_activity_spinner.setAdapter(arrayAdapter);

        // Add the "back" button to the toolbar
        Toolbar view_fitness_activity_toolbar = findViewById(R.id.edit_fitness_activity_toolbar);
        setSupportActionBar(view_fitness_activity_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles Checkbox logic
        check_auto_fill_title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_title.setEnabled(!isChecked);
                input_title.setText(input_fitness_activity_spinner.getSelectedItem().toString());
            }
        });

        if (getIntent().hasExtra(ViewFitnessActivityActivity.FROM_PARCEL)) {
            selectedFitnessActivity = getIntent().getParcelableExtra(ViewFitnessActivityActivity.FROM_PARCEL);
            Toast.makeText(this, "Loaded " + selectedFitnessActivity.getTitle(), Toast.LENGTH_LONG).show();
            updateText();
        }
    }

    /**
     * Called when left arrow button in the toolbar is pressed. Performs the same action as the
     * triangular-shaped "back" navigation button.
     *
     * @return True.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void updateText() {
        input_title.setText(selectedFitnessActivity.getTitle());
        input_description.setText(selectedFitnessActivity.getDescription());
        input_duration.setText(selectedFitnessActivity.getStart() + " " + selectedFitnessActivity.getEnd());
        input_calories.setText("" + selectedFitnessActivity.getCalories());

        check_auto_fill_title.setChecked(selectedFitnessActivity.isAutoInputTitle());
        check_manual_calories.setChecked(selectedFitnessActivity.isManualCalories());
        check_manual_end_time.setChecked(selectedFitnessActivity.isManualEndTime());
    }
}

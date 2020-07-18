package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    Slider input_intensity_slider;
    Map<String, Double> metData;
    Spinner input_fitness_activity_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fitness_activity);

        input_intensity_slider = findViewById(R.id.input_intensity_slider);
        input_fitness_activity_spinner = findViewById(R.id.input_fitness_activity_spinner);
        input_intensity_slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {

            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                if(slider.getValue() < 10) {
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
}

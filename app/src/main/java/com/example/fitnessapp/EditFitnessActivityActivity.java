package com.example.fitnessapp;

import android.os.Bundle;
import com.google.android.material.slider.Slider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditFitnessActivityActivity extends AppCompatActivity {

    Slider input_intensity_slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fitness_activity);

        input_intensity_slider = findViewById(R.id.input_intensity_slider);
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
    }
}

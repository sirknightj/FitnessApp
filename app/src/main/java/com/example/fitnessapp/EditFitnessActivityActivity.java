package com.example.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fitnessapp.models.FitnessActivity;

public class EditFitnessActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fitness_activity);

        assert getIntent().hasExtra("selected_fitness_activity");
        FitnessActivity selectedFitnessActivity = getIntent().getParcelableExtra("selected_fitness_activity");
        System.out.println(selectedFitnessActivity.getTitle());
    }
}

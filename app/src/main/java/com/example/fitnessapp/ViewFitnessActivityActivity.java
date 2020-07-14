package com.example.fitnessapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.models.FitnessActivity;

public class ViewFitnessActivityActivity extends AppCompatActivity {

    TextView fitactivity_title, fitactivity_description;
    FloatingActionButton edit_floating_action_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fitness_activity);

        assert getIntent().hasExtra("selected_fitness_activity");
        FitnessActivity selectedFitnessActivity = getIntent().getParcelableExtra("selected_fitness_activity");
        System.out.println(selectedFitnessActivity.getTitle());

        fitactivity_title = findViewById(R.id.fitactivity_title);
        fitactivity_description = findViewById(R.id.fitactivity_description);
        edit_floating_action_button = findViewById(R.id.edit_floating_action_button);

        fitactivity_title.setText(selectedFitnessActivity.getTitle());
        fitactivity_description.setText(selectedFitnessActivity.getDescription());

        edit_floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_floating_action_button.setEnabled(false);
                startActivity(new Intent(getApplicationContext(), EditFitnessActivityActivity.class));
            }
        });

        Toolbar view_fitness_activity_toolbar = findViewById(R.id.view_fitness_activity_toolbar);
        setSupportActionBar(view_fitness_activity_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        edit_floating_action_button.setEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

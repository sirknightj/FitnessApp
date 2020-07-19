package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.models.FitnessActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewFitnessActivityActivity extends AppCompatActivity {

    public static final String FROM_PARCEL = "editing_fitness_activity";

    private TextView fitactivity_title, fitactivity_description, fitactivity_timestamp, fitactivity_date, fitactivity_calories;
    private FloatingActionButton edit_floating_action_button;
    private FitnessActivity selectedFitnessActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fitness_activity);

        assert getIntent().hasExtra("selected_fitness_activity");
        selectedFitnessActivity = getIntent().getParcelableExtra("selected_fitness_activity");
        System.out.println(selectedFitnessActivity.getTitle());

        fitactivity_title = findViewById(R.id.fitactivity_title);
        fitactivity_description = findViewById(R.id.fitactivity_description);
        fitactivity_timestamp = findViewById(R.id.fitactivity_timestamp);
        fitactivity_date = findViewById(R.id.fitactivity_date);
        fitactivity_calories = findViewById(R.id.fitactivity_calories);
        edit_floating_action_button = findViewById(R.id.edit_floating_action_button);

        updateText();

        // Upon clicking the "edit this fitness activity" floating action button, this will
        // launch "activity_edit_fitness_activity" with pre-filled data.
        edit_floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_floating_action_button.setEnabled(false);
                Intent editActivity = new Intent(getApplicationContext(), EditFitnessActivityActivity.class);
                editActivity.putExtra(FROM_PARCEL, getSelectedFitnessActivity());
                startActivity(editActivity);
            }
        });

        // Add the "back" button to the toolbar
        Toolbar view_fitness_activity_toolbar = findViewById(R.id.view_fitness_activity_toolbar);
        setSupportActionBar(view_fitness_activity_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Called when this activity resumes. Updates the TextViews to contain the new properties of the fitness activity.
     */
    private void updateText() {
        fitactivity_title.setText(selectedFitnessActivity.getTitle());
        fitactivity_description.setText(selectedFitnessActivity.getDescription());
        fitactivity_timestamp.setText(selectedFitnessActivity.getStart() + " " + selectedFitnessActivity.getEnd());
        fitactivity_calories.setText(selectedFitnessActivity.getCalories() + " Cal");
    }

    /**
     * @return The selected fitness activity.
     */
    private FitnessActivity getSelectedFitnessActivity() {
        return selectedFitnessActivity;
    }

    /**
     * Called when this activity resumes. Re-enables the floating action button.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateText();
        edit_floating_action_button.setEnabled(true);
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

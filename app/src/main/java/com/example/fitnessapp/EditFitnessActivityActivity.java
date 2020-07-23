package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fitnessapp.models.FitnessActivity;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditFitnessActivityActivity extends AppCompatActivity {

    private EditText input_title, input_description, input_duration, input_calories, input_end_time;
    private CheckBox check_auto_fill_title, check_ending_now, check_manual_calories;
    private Slider input_intensity_slider;
    private Map<String, Double> metData;
    private Spinner input_fitness_activity_spinner;
    private FitnessActivity selectedFitnessActivity;
    private boolean isNewFitnessActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fitness_activity);

        input_title = findViewById(R.id.input_title);
        input_description = findViewById(R.id.input_description);
        input_duration = findViewById(R.id.input_duration);
        input_end_time = findViewById(R.id.input_end_time);
        input_calories = findViewById(R.id.input_calories);

        check_auto_fill_title = findViewById(R.id.check_auto_fill_title);
        check_ending_now = findViewById(R.id.check_ending_now);
        check_manual_calories = findViewById(R.id.check_automatic_calories);

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

        // Called whenever the item selected changes.
        input_fitness_activity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called whenever the item selected in the spinner changes.
             * @param parent The AdapterView.
             * @param view The view clicked in the AdapterView.
             * @param position The position of the clicked view in the AdapterView.
             * @param id The row id of the clicked view.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (check_auto_fill_title.isChecked()) {
                    updateTitle();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: Auto-generated method stub.
            }
        });

        input_fitness_activity_spinner.setAdapter(arrayAdapter);

        // Add the "back" button to the toolbar
        Toolbar view_fitness_activity_toolbar = findViewById(R.id.edit_fitness_activity_toolbar);
        setSupportActionBar(view_fitness_activity_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Handles Checkbox logic, with the title auto filling.
        check_auto_fill_title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_title.setEnabled(!isChecked);
                updateTitle();
            }
        });

        // Handles Checkbox logic, with the end time auto filling.
        check_ending_now.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_end_time.setEnabled(!isChecked);
                if (isChecked) {
                    setTimeNow();
                }
            }
        });

        // Handles Checkbox logic, with the automatic calorie calculations.
        check_manual_calories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                input_calories.setEnabled(!isChecked);
                if(isChecked) {
                    calculateCalories();
                }
            }
        });

        // Gets the incoming fitness activity, and one exists, fills in the boxes.
        isNewFitnessActivity = !getIntent().hasExtra(ViewFitnessActivityActivity.FROM_PARCEL);
        if (!isNewFitnessActivity) {
            selectedFitnessActivity = getIntent().getParcelableExtra(ViewFitnessActivityActivity.FROM_PARCEL);
            Toast.makeText(this, "Loaded " + selectedFitnessActivity.getTitle(), Toast.LENGTH_LONG).show();
            updateText();
        }

        // Creates the Date and Time pickers, when the date box is clicked.
        input_end_time.setOnClickListener(new View.OnClickListener() {
            /**
             * Called whenever input_end_time is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                input_end_time.setText(FitnessActivity.DATE_FORMAT.format(calendar.getTime()));
                            }
                        };
                        new TimePickerDialog(EditFitnessActivityActivity.this, R.style.DialogTheme, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
                    }
                };
                new DatePickerDialog(EditFitnessActivityActivity.this, R.style.DialogTheme, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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

    /**
     * Takes the incoming fitness activity and sets the presets for all the fields.
     */
    private void updateText() {
        input_title.setText(selectedFitnessActivity.getTitle());
        input_description.setText(selectedFitnessActivity.getDescription());
        input_duration.setText("" + selectedFitnessActivity.getDuration());
        input_calories.setText("" + selectedFitnessActivity.getCalories());

        check_auto_fill_title.setChecked(selectedFitnessActivity.isAutoInputTitle());
        check_manual_calories.setChecked(selectedFitnessActivity.isAutomaticCalories());
        check_ending_now.setChecked(selectedFitnessActivity.isAutomaticEndTime());

        input_fitness_activity_spinner.setSelection(FitActivityData.find(selectedFitnessActivity.getActivity()));
    }

    /**
     * Sets the title to the same name as the selected fitness activity in the spinner.
     */
    private void updateTitle() {
        input_title.setText(input_fitness_activity_spinner.getSelectedItem().toString());
    }

    /**
     * Sets the input_end_time to the current time.
     */
    private void setTimeNow() {
        input_end_time.setText(FitnessActivity.DATE_FORMAT.format(Calendar.getInstance().getTime()));
    }

    private void calculateCalories() {
        double calories = 0;
        double duration = 0;
        //TODO: CREATE METHOD TO UPDATE CALORIES
        if(input_duration.getText().toString().length() > 0) {
            duration = Double.parseDouble(input_duration.getText().toString());
        }
        calories = duration * FitActivityData.getMetData().get(input_fitness_activity_spinner.getSelectedItem().toString());
        input_calories.setText(Double.toString(calories));
    }
}

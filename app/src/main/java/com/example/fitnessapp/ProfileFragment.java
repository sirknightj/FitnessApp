package com.example.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the "edit my profile" screen. It lets you edit your height, weight, and age.
 */
public class ProfileFragment extends Fragment {

    private TextView bmi_output;
    private EditText input_weight, input_height, input_age;
    private boolean allowedToSave;
    private static final String MY_WEIGHT = "myWeightKey";
    private static final String MY_HEIGHT = "myHeightKey";
    private static final String MY_AGE = "myAge";

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Called when this view is created.
     *
     * @param savedInstanceState The saved instance of this.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called when this view is created, but after onCreate. Instantiates the user interface.
     *
     * @param inflater           The LayoutInflater used to inflate the contained views in this fragment.
     * @param container          The Parent view that this fragment is attached to.
     * @param savedInstanceState The saved instance of this.
     * @return This inflated View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Called immediately after onViewCreated. Initializes components in this view.
     *
     * @param view               This inflated View.
     * @param savedInstanceState The saved instance of this.
     */
    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        allowedToSave = false;
        bmi_output = view.findViewById(R.id.bmi_output);
        input_weight = view.findViewById(R.id.input_weight);
        input_height = view.findViewById(R.id.input_height);
        input_age = view.findViewById(R.id.input_age);

        // Watches for changes in the input_weight EditText, and saves changes, if applicable.
        input_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Automatically generated method stub. Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBMI();
            }

            @Override
            public void afterTextChanged(Editable s) {
                savePreferences();
            }
        });

        // Watches for changes in the input_height EditText, and saves changes, if applicable.
        input_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Automatically generated method stub. Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBMI();
            }

            @Override
            public void afterTextChanged(Editable s) {
                savePreferences();
            }
        });

        // Watches for changes in the input_age EditText, and saves changes, if applicable.
        input_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Automatically generated method stub. Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBMI();
            }

            @Override
            public void afterTextChanged(Editable s) {
                savePreferences();
            }
        });

        loadPreferences();
        updateBMI();
        allowedToSave = true;
    }

    /**
     * Recalculates the BMI, based on the height and weight.
     * BMI = weight * 703 / height^2
     */
    private void updateBMI() {
        if (input_weight.getText().toString().length() > 0 && input_height.getText().toString().length() > 0) {
            bmi_output.setText(Double.toString((double) Math.round((Double.parseDouble(input_weight.getText().toString()) / Math.pow(Double.parseDouble(input_height.getText().toString()), 2) * 703) * 100) / 100));
        } else {
            bmi_output.setText(R.string.error_not_enough_data);
        }
    }

    /**
     * Called when this View is resumed, after being paused.
     */
    @Override
    public void onResume() {
        super.onResume();
        allowedToSave = false;
        loadPreferences();
        allowedToSave = true;
    }

    /**
     * Loads in the height/weight/age data (fills the EditTexts).
     * Must be called after onViewCreated.
     */
    private void loadPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences.contains(MY_WEIGHT)) {
            input_weight.setText(Long.toString(preferences.getLong(MY_WEIGHT, 0)));
        }
        if (preferences.contains(MY_HEIGHT)) {
            input_height.setText(Long.toString(preferences.getLong(MY_HEIGHT, 0)));
        }
        if (preferences.contains(MY_AGE)) {
            input_age.setText(Long.toString(preferences.getLong(MY_AGE, 0)));
        }
    }

    /**
     * Saves the height/weight/age data. Must be called after onViewCreated.
     */
    private void savePreferences() {
        if (allowedToSave) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            if (input_weight.getText().toString().length() > 0) {
                editor.putLong(MY_WEIGHT, Long.parseLong(input_weight.getText().toString()));
            }
            if (input_height.getText().toString().length() > 0) {
                editor.putLong(MY_HEIGHT, Long.parseLong(input_height.getText().toString()));
            }
            if (input_age.getText().toString().length() > 0) {
                editor.putLong(MY_AGE, Long.parseLong(input_age.getText().toString()));
            }
            editor.apply();
            Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_LONG).show();
        }
    }
}

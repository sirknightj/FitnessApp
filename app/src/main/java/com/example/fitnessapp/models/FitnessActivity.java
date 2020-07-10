package com.example.fitnessapp.models;

import java.sql.Time;


/**
 * This is the model for a fitness activity.
 */
public class FitnessActivity {

    private String title;
    private String description;
    private Time start;
    private Time end;
    private int calories;

    /**
     * Constructor.
     *
     * @param title       The title of the activity.
     * @param description The description of the activity.
     * @param start       When the activity starts.
     * @param end         When the activity ends.
     */
    public FitnessActivity(String title, String description, Time start, Time end) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        calories = 0; // TODO: Use formula to find calories
    }

    /**
     * @return A string representation of the fitness activity.
     */
    @Override
    public String toString() {
        return "FitnessActivity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

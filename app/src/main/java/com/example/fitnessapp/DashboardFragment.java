package com.example.fitnessapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.models.FitnessActivity;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    GraphView graph_calories;
    LineGraphSeries<DataPoint> series_calories;
    List<FitnessActivity> fitnessActivities;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(List<FitnessActivity> fitnessActivities) {
        DashboardFragment dashboardFragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) fitnessActivities);
        dashboardFragment.setArguments(args);
        return dashboardFragment;
    }

    /**
     * Called when this view is created.
     *
     * @param savedInstanceState The saved instance of this.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             fitnessActivities = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    /**
     * Called immediately after onViewCreated. Initializes components in this view.
     *
     * @param view               This inflated View.
     * @param savedInstanceState The saved instance of this.
     */
    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        graph_calories = view.findViewById(R.id.graph_calories);
        series_calories = new LineGraphSeries<>(getCalorieData());
        graph_calories.addSeries(series_calories);
        graph_calories.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return FitnessActivity.DATE_FORMAT.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });
        graph_calories.getGridLabelRenderer().setHorizontalLabelsAngle(45);
    }

    /**
     * @return The calorie data to be plotted on the graph.
     */
    private DataPoint[] getCalorieData() {
        DataPoint[] calorie_data = new DataPoint[fitnessActivities.size()];
        Collections.sort(fitnessActivities);
        for (int i = 0; i < fitnessActivities.size(); i++) {
            try {
                calorie_data[i] = new DataPoint(FitnessActivity.DATE_FORMAT.parse(fitnessActivities.get(i).getEnd()).getTime(), fitnessActivities.get(i).getCalories());
            } catch (Exception e) {
                System.out.println("ERROR HERE!!");
                e.printStackTrace();
            }
        }
        return calorie_data;
    }
}

package com.example.fitnessapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.models.FitnessActivity;
import com.example.fitnessapp.recyclerViewAdapters.FitnessActivityRecyclerAdapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mainRecyclerView;
    private List<FitnessActivity> fitnessActivities;
    private FitnessActivityRecyclerAdapter fitnessActivityRecyclerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mainRecyclerView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    mainRecyclerView.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mainRecyclerView.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        mainRecyclerView = findViewById(R.id.main_recycler_view);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        insertTestingData();
        initializeRecyclerView();
    }

    /**
     * Populates the recyclerView with data for testing purposes.
     * TODO: DELETE THIS METHOD LATER.
     */
    private void insertTestingData() {
        fitnessActivities = new ArrayList<>();
        for(int i = 0; i < 60; i++) {
            fitnessActivities.add(new FitnessActivity("dab" + i, "desc", null, null));
        }
    }

    /**
     * Initializes the recyclerView.
     */
    private void initializeRecyclerView() {
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fitnessActivityRecyclerAdapter = new FitnessActivityRecyclerAdapter(fitnessActivities);
        mainRecyclerView.setAdapter(fitnessActivityRecyclerAdapter);
    }

}

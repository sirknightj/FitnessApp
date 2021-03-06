package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.models.FitnessActivity;
import com.example.fitnessapp.adapters.FitnessActivityRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.series.DataPoint;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is the controller for the activity that you see when the app is first opened.
 * Aka, this is the home screen of the app.
 */
public class MainActivity extends AppCompatActivity implements FitnessActivityRecyclerAdapter.OnFitnessActivityClickListener {

    private TextView mTextMessage;
    private RecyclerView mainRecyclerView;
    private List<FitnessActivity> fitnessActivities;
    private FitnessActivityRecyclerAdapter fitnessActivityRecyclerAdapter;
    private FrameLayout main_fragment_holder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mainRecyclerView.setVisibility(View.VISIBLE);
                    main_fragment_holder.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    mainRecyclerView.setVisibility(View.GONE);
                    main_fragment_holder.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_holder, DashboardFragment.newInstance(fitnessActivities)).commit();
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    mainRecyclerView.setVisibility(View.GONE);
                    main_fragment_holder.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_holder, new ProfileFragment()).commit();
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
        main_fragment_holder = findViewById(R.id.main_fragment_holder);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setSupportActionBar((Toolbar) findViewById(R.id.fitness_activity_toolbar));

        insertTestingData();
        initializeRecyclerView();

        try {
            FitActivityData.initializeData(getResources());
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR: FILE NOT FOUND", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Populates the recyclerView with data for testing purposes.
     * TODO: DELETE THIS METHOD LATER.
     */
    private void insertTestingData() {
        fitnessActivities = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            FitnessActivity dab = new FitnessActivity("dab" + i, "desc", "7/22/20 5:33 PM", "7/22/20 5:43 PM", "Jumping rope");
            dab.setCalories(5);
            FitnessActivity asdf = new FitnessActivity("dab2", "oooooo", "7/20/20 5:33 PM", "7/20/20 5:43 PM", "Jumping rope");
            asdf.setCalories(10);
            FitnessActivity asdsf = new FitnessActivity("dab2", "ooooooooooo", "7/23/20 5:33 PM", "7/23/20 5:43 PM", "Jumping rope");
            asdsf.setCalories(10);
            fitnessActivities.add(dab);
            fitnessActivities.add(asdf);
            fitnessActivities.add(asdsf);
        }
    }

    /**
     * Initializes the recyclerView.
     */
    private void initializeRecyclerView() {
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fitnessActivityRecyclerAdapter = new FitnessActivityRecyclerAdapter(fitnessActivities, this);
        mainRecyclerView.setAdapter(fitnessActivityRecyclerAdapter);
    }

    /**
     * Called whenever one of the fitness_activity_item's are clicked.
     *
     * @param position the position of the clicked fitness_activity_item in the recyclerView.
     */
    @Override
    public void onFitnessActivityClick(int position) {
        FitnessActivity clickedFitnessActivity = fitnessActivities.get(position);
        //TODO: DO OTHER THINGS!!

        Toast toast = Toast.makeText(getApplicationContext(), "Clicked " + fitnessActivities.get(position).getTitle(), Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, ViewFitnessActivityActivity.class);
        intent.putExtra("selected_fitness_activity", fitnessActivities.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        fitnessActivityRecyclerAdapter.notifyDataSetChanged();
    }
}

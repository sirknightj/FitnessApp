package com.example.fitnessapp;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FitActivityData {

    private static Map<String, Double> metData;
    private static Resources RESOURCES = Resources.getSystem();

    public static void initializeData(Resources resources) throws IOException {
        metData = new HashMap<>();
        InputStream inputStream = resources.openRawResource(R.raw.fit_activity_met_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        while(line != null) {
            double met = Double.parseDouble(reader.readLine());
            metData.put(line, met);
            line = reader.readLine();
        }
        metData = Collections.unmodifiableMap(metData);
        System.out.println(metData.toString());
    }

    public static Map<String, Double> getMetData() {
        return metData;
    }
}

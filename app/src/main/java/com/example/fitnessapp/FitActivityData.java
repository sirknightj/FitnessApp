package com.example.fitnessapp;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class manages all of the Fitness Activity metabolic equivalent data.
 */
public final class FitActivityData {

    private static Map<String, Double> metData; // An read-only map holding all of the values.

    /**
     * Must be called when the application is started. Reads the data from the file
     * (raw/fit_activity_met_data) and loads it into memory.
     *
     * @param resources The resources folder.
     * @throws IOException If the resource file is not found, or is modified while reading.
     */
    public static void initializeData(Resources resources) throws IOException {
        metData = new LinkedHashMap<>();
        InputStream inputStream = resources.openRawResource(R.raw.fit_activity_met_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        while (line != null) {
            double met = Double.parseDouble(reader.readLine());
            metData.put(line, met);
            line = reader.readLine();
        }
        metData = Collections.unmodifiableMap(metData);
        System.out.println(metData.toString());
    }

    /**
     * @return A read-only map holding all of the metabolic equivalent data.
     */
    public static Map<String, Double> getMetData() {
        return metData;
    }

    /**
     * Finds the key in the keyset of the metData. Returns the index.
     * Returns -1 if there's an error, or cannot find.
     *
     * @param key The key searched for.
     * @return The index of the key, starting from 0.
     */
    public static int find(String key) {
        assert metData.containsKey(key);
        int index = 0;
        for (String s : metData.keySet()) {
            if (s.equals(key)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}

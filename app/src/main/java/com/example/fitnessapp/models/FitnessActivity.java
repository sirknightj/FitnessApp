package com.example.fitnessapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * This is the model for a fitness activity.
 */
public class FitnessActivity implements Comparable<FitnessActivity>, Parcelable {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/d/yy h:m a");
    private String title, description, start, end;
    private int calories;
    private boolean isAutoInputTitle, isAutomaticCalories, isAutomaticEndTime;

    public boolean isAutoInputTitle() {
        return isAutoInputTitle;
    }

    public boolean isAutomaticCalories() {
        return isAutomaticCalories;
    }

    public void setAutoInputTitle(boolean autoInputTitle) {
        isAutoInputTitle = autoInputTitle;
    }

    public void setAutomaticCalories(boolean automaticCalories) {
        isAutomaticCalories = automaticCalories;
    }

    public void setAutomaticEndTime(boolean automaticEndTime) {
        isAutomaticEndTime = automaticEndTime;
    }

    public boolean isAutomaticEndTime() {
        return isAutomaticEndTime;
    }

    /**
     * Constructor. Creates FitnessActivityData when passed in a Parcel.
     *
     * @param in The parcel used to create the FitnessActivityData.
     */
    protected FitnessActivity(Parcel in) {
        title = in.readString();
        description = in.readString();
        start = in.readString();
        end = in.readString();
        calories = in.readInt();
        isAutoInputTitle = in.readByte() != 0;
        isAutomaticCalories = in.readByte() != 0;
        isAutomaticEndTime = in.readByte() != 0;
    }

    /**
     * Returns new FitnessActivities when passed in Parcels.
     */
    public static final Creator<FitnessActivity> CREATOR = new Creator<FitnessActivity>() {
        @Override
        public FitnessActivity createFromParcel(Parcel in) {
            return new FitnessActivity(in);
        }

        @Override
        public FitnessActivity[] newArray(int size) {
            return new FitnessActivity[size];
        }
    };

    /**
     * @return The title of this fitness activity.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of this fitness activity.
     *
     * @param title The new title of this fitness activity.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description of this fitness activity.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of this fitness activity.
     *
     * @param description The new description of this fitness activity.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The starting time of the fitness activity in long format.
     */
    public String getStart() {
        return start;
    }

    /**
     * Updates the starting time of the fitness activity. Must be in long format.
     *
     * @param start The new start time, in long format.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return The ending time of the fitness activity in long format.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Updates the ending time of the fitness activity. Must be in long format.
     *
     * @param end The new ending time, in long format.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return The number of calories burned during this fitness activity.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Updates the number of calories burned during this fitness activity.
     *
     * @param calories The new calorie number.
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Constructor. Creates a fitness activity with the creator's preferences.
     *
     * @param title       The title of the activity.
     * @param description The description of the activity.
     * @param start       When the activity starts.
     * @param end         When the activity ends.
     */
    public FitnessActivity(String title, String description, String start, String end) {
        this(title, description, start, end, true, true, true);
    }

    /**
     * Constructor.
     *
     * @param title               The title of the activity.
     * @param description         The description of the activity.
     * @param start               When the activity starts.
     * @param end                 When the activity ends.
     * @param isAutoInputTitle    True if the title should be the same as the activity.
     * @param isAutomaticCalories True if the calorie input is manual.
     * @param isManualEndTime     True if the activity's ending time is manual.
     */
    public FitnessActivity(String title, String description, String start, String end, boolean isAutoInputTitle, boolean isAutomaticCalories, boolean isManualEndTime) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.isAutoInputTitle = isAutoInputTitle;
        this.isAutomaticCalories = isAutomaticCalories;
        this.isAutomaticEndTime = isManualEndTime;
        calories = 0; // TODO: Use formula to find calories
    }

    /**
     * @return A string representation of the fitness activity.
     */
    @Override
    public String toString() {
        return "FitnessActivityData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    /**
     * Compares this FitnessActivityData to another FitnessActivityData.
     *
     * @param o the other FitnessActivityData to compare to.
     * @return a positive integer if this is larger than the other.
     * a negative integer if the other is larger than this.
     * 0 if they are equal.
     */
    @Override
    public int compareTo(FitnessActivity o) {
        return getStart().compareTo(o.getStart());
    }

    /**
     * Describes what type of special objects are in the Parcelable representation.
     *
     * @return 0, since we have nothing special.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Attaches the contents of this fitness activity to a Parcel.
     *
     * @param dest  The Parcel to be written to.
     * @param flags Additional flags about how this object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeInt(calories);
        dest.writeByte((byte) (isAutoInputTitle ? 1 : 0));
        dest.writeByte((byte) (isAutomaticCalories ? 1 : 0));
        dest.writeByte((byte) (isAutomaticEndTime ? 1 : 0));
    }
}

package com.example.fitnessapp.recyclerViewAdapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.models.FitnessActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class takes the data from the SQL database and populates the recyclerView with
 * the various items using the fitness_activity_item layout.
 */
public class FitnessActivityRecyclerAdapter extends RecyclerView.Adapter<FitnessActivityRecyclerAdapter.ViewHolder> {

    private List<FitnessActivity> fitnessActivities;
    private OnFitnessActivityClickListener onFitnessActivityClickListener;

    /**
     * Constructor.
     *
     * @param fitnessActivities The list of FitnessActivities to display.
     */
    public FitnessActivityRecyclerAdapter(List<FitnessActivity> fitnessActivities, OnFitnessActivityClickListener onFitnessActivityClickListener) {
        this.fitnessActivities = fitnessActivities;
        this.onFitnessActivityClickListener = onFitnessActivityClickListener;
    }

    /**
     * Called whenever the RecyclerView needs a new viewHolder to represent another item in the list.
     *
     * @param viewGroup The parent group in which the new View will be added to.
     * @param i         The new view's view type.
     * @return A ViewHolder containing the new view.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View activityItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fitness_activity_item, viewGroup, false);
        return new ViewHolder(activityItemView, onFitnessActivityClickListener);
    }

    /**
     * Called by the RecyclerView to display items at a certain position. Sets all of the contents to be displayed
     * in each fitness_activity_item.
     *
     * @param viewHolder The viewHolder to display, containing the contents of each FitnessActivity.
     * @param i          The position of the item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FitnessActivity currentActivity = fitnessActivities.get(i);
        viewHolder.title.setText(currentActivity.getTitle());
        viewHolder.description.setText(currentActivity.getDescription());
        viewHolder.timestamp.setText(currentActivity.getStart() + " - " + currentActivity.getEnd());
        viewHolder.calories.setText(currentActivity.getCalories() + " cal");
    }

    /**
     * @return the total number of fitness_activity_item's the RecyclerView should display.
     */
    @Override
    public int getItemCount() {
        return fitnessActivities.size();
    }

    /**
     * This is the class for each individual fitness_activity_item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, description, calories, timestamp;
        private OnFitnessActivityClickListener onFitnessActivityClickListener;
        private boolean clicked;

        /**
         * Constructor. Provides a reference to the views for each property of a FitnessActivity.
         */
        public ViewHolder(@NonNull View itemView, OnFitnessActivityClickListener onFitnessActivityClickListener) {
            super(itemView);

            this.onFitnessActivityClickListener = onFitnessActivityClickListener;
            itemView.setOnClickListener(this);
            itemView.setClickable(true);

            title = itemView.findViewById(R.id.fitactivity_title);
            description = itemView.findViewById(R.id.fitactivity_description);
            timestamp = itemView.findViewById(R.id.fitactivity_timestamp);
            calories = itemView.findViewById(R.id.fitactivity_calories);
        }


        /**
         * Called whenever the fitness_activity_item is clicked.
         * @param v the view being clicked.
         */
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            onFitnessActivityClickListener.onFitnessActivityClick(getAdapterPosition());
        }
    }

    /**
     * Interface for the onClickListener. Sends the clicked data
     */
    public interface OnFitnessActivityClickListener {

        /**
         * This is called whenever one of the fitness_activity_item's are clicked in the recyclerView.
         *
         * @param position The position of the clicked FitnessActivity item in the recyclerView.
         */
        void onFitnessActivityClick(int position);
    }
}

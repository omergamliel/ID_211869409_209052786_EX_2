package co.median.android.id_211869409_209052786_ex_2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;

/**
 * Adapter used in edit mode for displaying historical activities with update
 * and delete options.
 */
public class ManageActivitiesAdapter extends RecyclerView.Adapter<ManageActivitiesAdapter.ViewHolder> {

    public interface OnActivityActionListener {
        void onEdit(Activity activity);
        void onDelete(Activity activity);
    }

    private final List<Activity> activities;
    private final OnActivityActionListener listener;

    public ManageActivitiesAdapter(List<Activity> activities, OnActivityActionListener listener) {
        this.activities = activities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(activities.get(position));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        Button updateButton;
        Button deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.activity_title_textview);
            descriptionTextView = itemView.findViewById(R.id.activity_description_textview);
            dateTextView = itemView.findViewById(R.id.activity_date_textview);
            deleteButton = itemView.findViewById(R.id.delete_activity_button);
            updateButton = itemView.findViewById(R.id.update_activity_button);
        }

        void bind(Activity activity) {
            titleTextView.setText(activity.getTitle());
            descriptionTextView.setText(activity.getDescription());
            dateTextView.setText(activity.getHistoricalDate());

            updateButton.setOnClickListener(v -> listener.onEdit(activity));
            deleteButton.setOnClickListener(v -> listener.onDelete(activity));
        }
    }
}

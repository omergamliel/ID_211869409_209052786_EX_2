package co.median.android.id_211869409_209052786_ex_2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {

    private final List<Activity> activityList;
    private final List<Activity> selectedActivities = new ArrayList<>();

    public ActivitiesAdapter(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity currentActivity = activityList.get(position);
        holder.bind(currentActivity);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;
        private final CheckBox checkBox;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.activity_title_textview);
            descriptionTextView = itemView.findViewById(R.id.activity_description_textview);
            dateTextView = itemView.findViewById(R.id.activity_date_textview);
            checkBox = itemView.findViewById(R.id.activity_checkbox);
        }

        void bind(final Activity activity) {
            titleTextView.setText(activity.getTitle());
            descriptionTextView.setText(activity.getDescription());
            dateTextView.setText(activity.getHistoricalDate());

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(selectedActivities.contains(activity));

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedActivities.contains(activity)) {
                        selectedActivities.add(activity);
                    }
                } else {
                    selectedActivities.remove(activity);
                }
            });
        }
    }
}
package co.median.android.id_211869409_209052786_ex_2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;

public class PrioritySelectionAdapter extends RecyclerView.Adapter<PrioritySelectionAdapter.ViewHolder> {

    private final List<Activity> activities;
    private final List<Activity> selectedActivities = new ArrayList<>();

    public PrioritySelectionAdapter(List<Activity> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_priority_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.titleTextView.setText(activity.getTitle());

        holder.checkBox.setOnCheckedChangeListener(null); 
        holder.checkBox.setChecked(selectedActivities.contains(activity));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (selectedActivities.size() < 3) {
                    selectedActivities.add(activity);
                } else {
                    buttonView.setChecked(false);
                    Toast.makeText(buttonView.getContext(), "ניתן לבחור עד 3 בלבד", Toast.LENGTH_SHORT).show();
                }
            } else {
                selectedActivities.remove(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.priority_activity_title);
            checkBox = itemView.findViewById(R.id.priority_checkbox);
        }
    }
}

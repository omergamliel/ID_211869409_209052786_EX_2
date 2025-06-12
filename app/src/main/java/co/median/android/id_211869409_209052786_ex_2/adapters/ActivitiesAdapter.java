// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.adapters;

// Adapter המציג את רשימת הפעילויות לבחירה במסך.

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

    // בנאי שמקבל את רשימת הפעילויות להצגה
    public ActivitiesAdapter(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    // יצירת ViewHolder לפריטי הרשימה
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    // קשירת הנתונים לתצוגה
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity currentActivity = activityList.get(position);
        holder.bind(currentActivity);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    // החזרת רשימת הפעילויות שסומנו
    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;
        private final CheckBox checkBox;

        // בנאי ViewHolder שמאתחל את רכיבי התצוגה
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.activity_title_textview);
            descriptionTextView = itemView.findViewById(R.id.activity_description_textview);
            dateTextView = itemView.findViewById(R.id.activity_date_textview);
            checkBox = itemView.findViewById(R.id.activity_checkbox);
        }

        //  קישור נתוני הפעילות לאלמנטים ברשימה והוספה או הורדה בהתאם לסימון הצ'ק בוקס
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
// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט לבחירת פעילויות היסטוריות על ידי המשתמש.

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.activities.NormalModeActivity;
import co.median.android.id_211869409_209052786_ex_2.adapters.ActivitiesAdapter;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;
import co.median.android.id_211869409_209052786_ex_2.utils.PreferenceManager;

public class ActivitiesSelectionFragment extends Fragment {

    private RecyclerView activitiesRecyclerView;
    private Button nextButton;
    private ActivitiesAdapter activitiesAdapter;
    private DatabaseHelper dbHelper;
    private PreferenceManager preferenceManager;

    @Nullable
    @Override
    // יצירת תצוגת בחירת הפעילויות
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activities_selection, container, false);
    }

    @Override
    // לאחר יצירת התצוגה נטענות פעילויות ומוגדר לחצן ההמשך
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());
        dbHelper = new DatabaseHelper(requireContext());

        activitiesRecyclerView = view.findViewById(R.id.activities_recycler_view);
        nextButton = view.findViewById(R.id.next_button);

        setupRecyclerView();
        setupNextButton();
    }

    // הגדרת רשימת הפעילויות למסך
    private void setupRecyclerView() {
        List<Activity> realActivities = loadActivitiesFromDB();
        activitiesAdapter = new ActivitiesAdapter(realActivities);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        activitiesRecyclerView.setAdapter(activitiesAdapter);
    }

    // שליפת כל הפעילויות ממסד הנתונים
    private List<Activity> loadActivitiesFromDB() {
        List<Activity> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllActivities();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        list.add(new Activity(id, title, description, date));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return list;
    }

    // טיפול בלחיצה על כפתור ההמשך ובהצגת דיאלוג העדיפויות
    private void setupNextButton() {
        nextButton.setOnClickListener(v -> {
            List<Activity> selected = activitiesAdapter.getSelectedActivities();

            if (selected.size() > 3) {
                ActivitiesPriorityDialogFragment dialog = ActivitiesPriorityDialogFragment.newInstance(new ArrayList<>(selected));
                dialog.show(getParentFragmentManager(), "priority_dialog");

                getParentFragmentManager().setFragmentResultListener(
                        ActivitiesPriorityDialogFragment.RESULT_KEY,
                        getViewLifecycleOwner(),
                        (requestKey, result) -> {
                            ArrayList<Integer> priorityIds = result.getIntegerArrayList(ActivitiesPriorityDialogFragment.RESULT_SELECTED_IDS);
                            if (priorityIds != null) {
                                preferenceManager.savePriorityActivityIds(priorityIds);
                                preferenceManager.saveSelectedActivities(selected);

                                if (getActivity() instanceof NormalModeActivity) {
                                    NormalModeActivity activity = (NormalModeActivity) getActivity();
                                    activity.getCurrentTravel().setSelectedActivities(selected); 
                                    activity.navigateToEraSelection();
                                }
                            }
                        });
            } else {
                preferenceManager.saveSelectedActivities(selected);

                if (getActivity() instanceof NormalModeActivity) {
                    NormalModeActivity activity = (NormalModeActivity) getActivity();
                    activity.getCurrentTravel().setSelectedActivities(selected); 
                    activity.navigateToEraSelection();
                }
            }
        });
    }



}

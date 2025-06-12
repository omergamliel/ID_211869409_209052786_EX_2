package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט לניהול רשימת הפעילויות במסד הנתונים.

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.adapters.ManageActivitiesAdapter;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;


public class ActivitiesManageFragment extends Fragment implements ManageActivitiesAdapter.OnActivityActionListener {

    private RecyclerView recyclerView;
    private Button addButton;
    private ManageActivitiesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    // יצירת ממשק ניהול הפעילויות
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activities_manage, container, false);
    }

    @Override
    // אתחול הרשימה והכפתורים לאחר יצירת הממשק
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
        recyclerView = view.findViewById(R.id.activities_recycler_view);
        addButton = view.findViewById(R.id.add_activity_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ManageActivitiesAdapter(loadActivities(), this);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> openDialog(null));
    }

    // טוען את הפעילויות הקיימות מהמסד
    private List<Activity> loadActivities() {
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

    // רענון הרשימה לאחר שינוי
    private void refresh() {
        adapter = new ManageActivitiesAdapter(loadActivities(), this);
        recyclerView.setAdapter(adapter);
    }

    // פתיחת דיאלוג להוספה או עדכון של פעילות
    private void openDialog(@Nullable Activity activity) {
        ManageActivityDialogFragment dialog = ManageActivityDialogFragment.newInstance(activity);
        dialog.setOnSaveListener(this::refresh);
        dialog.show(getParentFragmentManager(), "manage_activity");
    }

    @Override
    // אירוע עריכת פעילות קיימת
    public void onEdit(Activity activity) {
        openDialog(activity);
    }

    @Override
    // אירוע מחיקת פעילות קיימת
    public void onDelete(Activity activity) {
        dbHelper.deleteActivity(activity.getId());
        refresh();
    }
}

package co.median.android.id_211869409_209052786_ex_2.fragments;

// דיאלוג המאפשר לבחור שלוש פעילויות מועדפות מבין הבחירות.

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.adapters.PrioritySelectionAdapter;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;

public class ActivitiesPriorityDialogFragment extends DialogFragment {

    public static final String ARG_ACTIVITIES = "selected_activities";
    public static final String RESULT_KEY = "priority_result";
    public static final String RESULT_SELECTED_IDS = "selected_ids";

    private PrioritySelectionAdapter adapter;

    // יצירת דיאלוג עם רשימת הפעילויות שנבחרו
    public static ActivitiesPriorityDialogFragment newInstance(ArrayList<Activity> selectedActivities) {
        ActivitiesPriorityDialogFragment fragment = new ActivitiesPriorityDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVITIES, selectedActivities);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    // בניית הדיאלוג לבחירת שלוש פעילויות בעדיפות גבוהה
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_activities_priority, null);
        RecyclerView recyclerView = view.findViewById(R.id.priority_recycler_view);
        Button confirmButton = view.findViewById(R.id.confirm_priority_button);

        List<Activity> activities = (List<Activity>) getArguments().getSerializable(ARG_ACTIVITIES);
        adapter = new PrioritySelectionAdapter(activities);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        confirmButton.setOnClickListener(v -> {
            List<Activity> selected = adapter.getSelectedActivities();
            if (selected.size() != 3) {
                Toast.makeText(getContext(), "יש לבחור בדיוק 3 פעילויות", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Integer> selectedIds = new ArrayList<>();
                for (Activity a : selected) {
                    selectedIds.add(a.getId());
                }
                Bundle result = new Bundle();
                result.putIntegerArrayList(RESULT_SELECTED_IDS, selectedIds);
                getParentFragmentManager().setFragmentResult(RESULT_KEY, result);
                dismiss();
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setTitle("בחר 3 פעילויות מועדפות");
        return dialog;
    }
}

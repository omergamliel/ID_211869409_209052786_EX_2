package co.median.android.id_211869409_209052786_ex_2.fragments;

// דיאלוג להוספה או עריכה של פעילות היסטורית במסד הנתונים.

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;


public class ManageActivityDialogFragment extends DialogFragment {

    private static final String ARG_ACTIVITY = "activity";

    public interface OnSaveListener {
        void onSave();
    }

    private Activity activity;
    private OnSaveListener listener;
    private DatabaseHelper dbHelper;

    // יצירת מופע חדש של הדיאלוג עם אובייקט פעילות (אם קיים)
    public static ManageActivityDialogFragment newInstance(@Nullable Activity activity) {
        ManageActivityDialogFragment fragment = new ManageActivityDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVITY, activity);
        fragment.setArguments(args);
        return fragment;
    }

    // הגדרת מאזין לשמירה כדי לרענן את הרשימה במסך הקודם
    public void setOnSaveListener(OnSaveListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    // בניית הדיאלוג להצגת הטופס ולשמירת הנתונים
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_manage_activity, null);
        EditText titleInput = view.findViewById(R.id.input_title);
        EditText descInput = view.findViewById(R.id.input_description);
        TextView dateText = view.findViewById(R.id.selected_date);
        Button pickDateButton = view.findViewById(R.id.pick_date_button);
        Button saveButton = view.findViewById(R.id.save_activity_button);

        dbHelper = new DatabaseHelper(requireContext());
        if (getArguments() != null) {
            activity = (Activity) getArguments().getSerializable(ARG_ACTIVITY);
        }

        final String[] selectedDate = {null};

        if (activity != null) {
            titleInput.setText(activity.getTitle());
            descInput.setText(activity.getDescription());
            dateText.setText(activity.getHistoricalDate());
            selectedDate[0] = activity.getHistoricalDate();
        }

        pickDateButton.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view1, year, month, day) -> {
                selectedDate[0] = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
                dateText.setText(selectedDate[0]);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();
            if (title.isEmpty() || selectedDate[0] == null) {
                Toast.makeText(getContext(), "יש למלא כותרת ותאריך", Toast.LENGTH_SHORT).show();
                return;
            }
            if (activity == null) {
                dbHelper.insertActivity(title, desc, selectedDate[0]);
            } else {
                dbHelper.updateActivity(activity.getId(), title, desc, selectedDate[0]);
            }
            if (listener != null) listener.onSave();
            dismiss();
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(view);
        dialog.setTitle(activity == null ? "הוספת פעילות" : "עדכון פעילות");
        return dialog;
    }
}

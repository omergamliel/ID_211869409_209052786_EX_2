package co.median.android.id_211869409_209052786_ex_2.fragments;

// דיאלוג המאשר את פרטי המסע לפני היציאה אליו.

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialogFragment extends DialogFragment {

    public static final String REQUEST_KEY = "confirmationRequest";
    public static final String KEY_RESPONSE = "userConfirmation";

    private static final String ARG_SUMMARY_MESSAGE = "summaryMessage";

    // יצירת מופע חדש של הדיאלוג עם טקסט הסיכום
    public static ConfirmationDialogFragment newInstance(String summaryMessage) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUMMARY_MESSAGE, summaryMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    // בניית הדיאלוג והחזרת תוצאת המשתמש
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String summaryMessage = "";
        if (getArguments() != null) {
            summaryMessage = getArguments().getString(ARG_SUMMARY_MESSAGE, "Error loading summary.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("אישור פרטי המסע")
                .setMessage(summaryMessage)
                .setPositiveButton("אשר וצא למסע", (dialog, id) -> {
                    
                    Bundle result = new Bundle();
                    result.putBoolean(KEY_RESPONSE, true);
                    getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
                })
                .setNegativeButton("ביטול", (dialog, id) -> {
                    
                });

        return builder.create();
    }
}
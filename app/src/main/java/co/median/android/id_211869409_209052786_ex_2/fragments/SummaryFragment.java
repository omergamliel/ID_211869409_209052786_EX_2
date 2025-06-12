// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט המציג סיכום של המסע שנבחר ומאפשר שיתוף או איפוס.

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.StringJoiner;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.activities.MainActivity;
import co.median.android.id_211869409_209052786_ex_2.activities.NormalModeActivity;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;
import co.median.android.id_211869409_209052786_ex_2.models.Travel;
import co.median.android.id_211869409_209052786_ex_2.utils.PreferenceManager;

public class SummaryFragment extends Fragment {

    private TextView dateTextView, timeTextView, eraTextView;
    private LinearLayout activitiesContainer;
    private Button shareButton, resetButton;

    private DatabaseHelper dbHelper;
    private PreferenceManager preferenceManager;

    private String summaryTextForShare;

    @Nullable
    @Override
    // יצירת ממשק הפרגמנט
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    // לאחר יצירת הממשק נטען נתונים ומגדירים לחצנים
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());
        preferenceManager = new PreferenceManager(requireContext());

        initializeViews(view);
        displaySummaryAndSave();
        setupButtons();
    }

    // אתחול רכיבי התצוגה במסך
    private void initializeViews(View view) {
        dateTextView = view.findViewById(R.id.summary_date_textview);
        timeTextView = view.findViewById(R.id.summary_time_textview);
        eraTextView = view.findViewById(R.id.summary_era_textview);
        activitiesContainer = view.findViewById(R.id.summary_activities_container);
        shareButton = view.findViewById(R.id.share_button);
        resetButton = view.findViewById(R.id.reset_button);
    }

    // הצגת הסיכום ושמירתו במסד הנתונים
    private void displaySummaryAndSave() {
        if (!(getActivity() instanceof NormalModeActivity)) return;

        Travel travel = ((NormalModeActivity) getActivity()).getCurrentTravel();

        //לקיחת זמן ותאריך מהshared preferences
        travel.setSelectedDate(preferenceManager.getSelectedDate());
        travel.setSelectedTime(preferenceManager.getSelectedTime());

        
        if (travel.getSelectedDate() == null || travel.getSelectedTime() == null || travel.getSelectedEra() == null) {
            Toast.makeText(getContext(), "שגיאה: חסר מידע על המסע.", Toast.LENGTH_LONG).show();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            return;
        }

        
        dateTextView.setText("תאריך: " + travel.getSelectedDate());
        timeTextView.setText("שעה: " + travel.getSelectedTime());
        eraTextView.setText("תקופה: " + travel.getSelectedEra());

        activitiesContainer.removeAllViews();
        //איסוף של כל הפעילויות ההיסטוריות
        StringJoiner activitiesJoiner = new StringJoiner(", ");
        for (Activity act : travel.getSelectedActivities()) {
            TextView activityView = new TextView(requireContext());
            activityView.setText("- " + act.getTitle());
            activityView.setTextSize(16);
            activitiesContainer.addView(activityView);
            activitiesJoiner.add(String.valueOf(act.getId()));
        }

        long result = dbHelper.insertTravel(travel);
        if (result != -1) {
            Toast.makeText(getContext(), "המסע נשמר ביומן!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "שגיאה בשמירת המסע.", Toast.LENGTH_SHORT).show();
        }

        
        prepareShareText(travel, activitiesJoiner.toString());
    }

    // הגדרת פעולות הכפתורים
    private void setupButtons() {
        shareButton.setOnClickListener(v -> shareSummary());
        resetButton.setOnClickListener(v -> resetJourney());
    }

    // שיתוף הסיכום באמצעות Intent
    private void shareSummary() {
        if (summaryTextForShare == null || summaryTextForShare.isEmpty()) {
            Toast.makeText(getContext(), "אין מידע לשיתוף", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, summaryTextForShare);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "שתף את המסע שלך באמצעות...");
        startActivity(shareIntent);
    }

    // איפוס כל הבחירות וחזרה למסך הראשי
    private void resetJourney() {
        preferenceManager.clearPreferences();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    // הכנת הטקסט לשיתוף המסע
    private void prepareShareText(Travel travel, String activityIds) {
        summaryTextForShare = "יומן המסע שלי בזמן:\n" +
                "תאריך: " + travel.getSelectedDate() + "\n" +
                "שעה: " + travel.getSelectedTime() + "\n" +
                "תקופה: " + travel.getSelectedEra() + "\n" +
                "מזהי פעילויות: " + activityIds;
    }
}

// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט לבחירת תאריך ושעה למסע של המשתמש.

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.activities.NormalModeActivity;
import co.median.android.id_211869409_209052786_ex_2.utils.PreferenceManager;

public class DateTimePickerFragment extends Fragment {

    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;
    private Button selectDateButton;
    private Button selectTimeButton;
    private Button nextButton;

    private PreferenceManager preferenceManager;
    private String selectedDate = null;
    private String selectedTime = null;

    @Nullable
    @Override
    // יצירת תצוגת בחירת התאריך והשעה
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_time_picker, container, false);
    }

    @Override
    // לאחר יצירת התצוגה נטענים הערכים השמורים ומוגדרים המאזינים
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        selectedDateTextView = view.findViewById(R.id.selected_date_textview);
        selectedTimeTextView = view.findViewById(R.id.selected_time_textview);
        selectDateButton = view.findViewById(R.id.select_date_button);
        selectTimeButton = view.findViewById(R.id.select_time_button);
        nextButton = view.findViewById(R.id.next_button);

        loadPreferences();
        setupClickListeners();
        updateNextButtonState();
    }

    // טוען ערכים קיימים מההעדפות ומשקף אותם במסך
    private void loadPreferences() {
        selectedDate = preferenceManager.getSelectedDate();
        if (selectedDate != null && !selectedDate.isEmpty()) {
            selectedDateTextView.setText("תאריך המסע: " + selectedDate);
        }

        selectedTime = preferenceManager.getSelectedTime();
        if (selectedTime != null && !selectedTime.isEmpty()) {
            selectedTimeTextView.setText("שעת המסע: " + selectedTime);
        }
    }

    // הגדרת פעולות ללחצני בחירת תאריך ושעה והמשך
    private void setupClickListeners() {
        selectDateButton.setOnClickListener(v -> openDatePicker());
        selectTimeButton.setOnClickListener(v -> openTimePicker());

        nextButton.setOnClickListener(v -> {
            if (getActivity() instanceof NormalModeActivity) {
                ((NormalModeActivity) getActivity()).navigateToActivitiesSelection();
            }
        });

    }

    // פתיחת דיאלוג לבחירת תאריך
    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month1 + 1, year1);
            selectedDateTextView.setText("תאריך המסע: " + selectedDate);
            preferenceManager.saveSelectedDate(selectedDate);
            updateNextButtonState();
        }, year, month, day);

        datePickerDialog.show();
    }

    // פתיחת דיאלוג לבחירת שעה
    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minuteOfHour) -> {
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
            selectedTimeTextView.setText("שעת המסע: " + selectedTime);
            preferenceManager.saveSelectedTime(selectedTime);
            updateNextButtonState();
        }, hour, minute, true);

        timePickerDialog.show();
    }

    // הפעלת כפתור ההמשך רק כאשר קיימים תאריך ושעה
    private void updateNextButtonState() {
        boolean isEnabled = selectedDate != null && !selectedDate.isEmpty() &&
                selectedTime != null && !selectedTime.isEmpty();
        nextButton.setEnabled(isEnabled);
    }
}
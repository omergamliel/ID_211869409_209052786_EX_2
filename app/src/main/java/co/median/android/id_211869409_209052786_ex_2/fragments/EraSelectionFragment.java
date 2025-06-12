// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט לבחירת התקופה ההיסטורית הרצויה למסע.

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.activities.NormalModeActivity;
import co.median.android.id_211869409_209052786_ex_2.adapters.EraAdapter;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Era;
import co.median.android.id_211869409_209052786_ex_2.utils.PreferenceManager;

public class EraSelectionFragment extends Fragment implements EraAdapter.OnEraSelectedListener {

    private Button nextButton;
    private RecyclerView erasRecyclerView;
    private PreferenceManager preferenceManager;
    private DatabaseHelper dbHelper;
    private Era selectedEra = null;

    @Nullable
    @Override
    // יצירת תצוגת בחירת התקופה
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_era_selection, container, false);
    }

    @Override
    // טעינת הנתונים לאחר יצירת התצוגה
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());
        dbHelper = new DatabaseHelper(requireContext());

        nextButton = view.findViewById(R.id.next_button);
        erasRecyclerView = view.findViewById(R.id.eras_recycler_view);

        setupRecyclerView();
        setupNextButton();
        setupFragmentResultListener();

        nextButton.setEnabled(false); 
    }

    @Override
    // פעולה לאחר בחירת תקופה מרשימת התקופות
    public void onEraSelected(Era era) {
        this.selectedEra = era;

        
        preferenceManager.saveSelectedEraName(era.getName());

        
        if (getActivity() instanceof NormalModeActivity) {
            NormalModeActivity activity = (NormalModeActivity) getActivity();
            activity.getCurrentTravel().setSelectedEra(era.getName());
        }

        
        nextButton.setEnabled(true);
    }

    // הגדרת רשימת התקופות מהמסד
    private void setupRecyclerView() {
        EraAdapter eraAdapter = new EraAdapter(loadErasFromDB(), this);
        erasRecyclerView.setAdapter(eraAdapter);
    }

    // מאזין לכפתור ההמשך הפותח דיאלוג אישור
    private void setupNextButton() {
        nextButton.setOnClickListener(v -> showConfirmationDialog());
    }

    // קבלת תוצאות מדיאלוג האישור
    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                ConfirmationDialogFragment.REQUEST_KEY, this, (requestKey, bundle) -> {
                    boolean userConfirmed = bundle.getBoolean(ConfirmationDialogFragment.KEY_RESPONSE);
                    if (userConfirmed) {
                        
                        if (getActivity() instanceof NormalModeActivity) {
                            ((NormalModeActivity) getActivity()).navigateToSummary();
                        }
                    }
                });
    }

    // שליפת תקופות ממסד הנתונים
    private List<Era> loadErasFromDB() {
        List<Era> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllEras();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String imageName = cursor.getString(cursor.getColumnIndexOrThrow("image_name"));

                        int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
                        if (imageResId == 0) {
                            imageResId = R.drawable.logo; 
                        }

                        list.add(new Era(name, imageResId));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return list;
    }

    // הצגת דיאלוג לאישור המסע לפני סיום
    private void showConfirmationDialog() {
        if (selectedEra == null) return;

        
        String date = preferenceManager.getSelectedDate();
        String time = preferenceManager.getSelectedTime();
        String eraName = preferenceManager.getSelectedEraName();
        List<String> activityIds = preferenceManager.getSelectedActivityIds();

        String summaryMessage = "האם לאשר את המסע הבא?\n\n" +
                "תאריך: " + (date != null ? date : "לא נבחר") + "\n" +
                "שעה: " + (time != null ? time : "לא נבחרה") + "\n" +
                "תקופה: " + (eraName != null ? eraName : "לא נבחרה") + "\n\n" +
                "מספר פעילויות שנבחרו: " + activityIds.size();

        ConfirmationDialogFragment dialog = ConfirmationDialogFragment.newInstance(summaryMessage);
        dialog.show(getParentFragmentManager(), "ConfirmationDialog");
    }
}

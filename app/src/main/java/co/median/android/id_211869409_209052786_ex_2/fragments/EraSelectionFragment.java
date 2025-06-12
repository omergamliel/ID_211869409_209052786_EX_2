package co.median.android.id_211869409_209052786_ex_2.fragments;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_era_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());
        dbHelper = new DatabaseHelper(requireContext());

        nextButton = view.findViewById(R.id.next_button);
        erasRecyclerView = view.findViewById(R.id.eras_recycler_view);

        setupRecyclerView();
        setupNextButton();
        setupFragmentResultListener();

        nextButton.setEnabled(false); // הכפתור יופעל רק אחרי בחירה
    }

    @Override
    public void onEraSelected(Era era) {
        this.selectedEra = era;

        // 1. שמור את התקופה ב־SharedPreferences (כנדרש במטלה)
        preferenceManager.saveSelectedEraName(era.getName());

        // 2. עדכן את Travel באקטיביטי עם שם התקופה
        if (getActivity() instanceof NormalModeActivity) {
            NormalModeActivity activity = (NormalModeActivity) getActivity();
            activity.getCurrentTravel().setSelectedEra(era.getName());
        }

        // 3. הפעל כפתור "הבא"
        nextButton.setEnabled(true);
    }

    private void setupRecyclerView() {
        EraAdapter eraAdapter = new EraAdapter(loadErasFromDB(), this);
        erasRecyclerView.setAdapter(eraAdapter);
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(v -> showConfirmationDialog());
    }

    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                ConfirmationDialogFragment.REQUEST_KEY, this, (requestKey, bundle) -> {
                    boolean userConfirmed = bundle.getBoolean(ConfirmationDialogFragment.KEY_RESPONSE);
                    if (userConfirmed) {
                        // ניווט למסך הסיכום
                        if (getActivity() instanceof NormalModeActivity) {
                            ((NormalModeActivity) getActivity()).navigateToSummary();
                        }
                    }
                });
    }

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
                            imageResId = R.drawable.logo; // fallback
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

    private void showConfirmationDialog() {
        if (selectedEra == null) return;

        // שלוף את הנתונים מ־SharedPreferences לצורך סיכום
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

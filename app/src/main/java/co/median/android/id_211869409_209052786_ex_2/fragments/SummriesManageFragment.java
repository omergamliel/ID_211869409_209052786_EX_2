// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.fragments;

// פרגמנט לניהול והצגת סיכומי מסע קיימים.

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

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.database.DatabaseHelper;
import co.median.android.id_211869409_209052786_ex_2.models.Travel;

public class SummriesManageFragment extends Fragment {

    private LinearLayout summariesContainer;
    private Button deleteButton;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    // יצירת תצוגת ניהול הסיכומים
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summrie_manage, container, false);
    }

    @Override
    // לאחר יצירת התצוגה נטענים הסיכומים ומוגדר כפתור המחיקה
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());

        summariesContainer = view.findViewById(R.id.summaries_container); 
        deleteButton = view.findViewById(R.id.delete_summries_button);

        //העלאת כל סיכומי המסע

        loadSummaries();
        //מחיקת כל הסיכומים
        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteAllSummaries();
            Toast.makeText(requireContext(), "מחיקת כל הסיכומים בוצעה", Toast.LENGTH_SHORT).show();
            loadSummaries();
        });
    }

    // טעינת הסיכומים מהמסד והצגתם
    private void loadSummaries() {
        summariesContainer.removeAllViews();
        List<Travel> travels = dbHelper.getAllSummaries();

        if (travels.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("אין סיכומים להצגה.");
            emptyView.setTextSize(16);
            summariesContainer.addView(emptyView);
            return;
        }

        for (Travel travel : travels) {
            TextView summaryView = new TextView(requireContext());
            summaryView.setTextSize(16);
            summaryView.setPadding(8, 8, 8, 8);
//יצירת סיכום
            StringBuilder sb = new StringBuilder();
            sb.append("תאריך: ").append(travel.getSelectedDate()).append("\n");
            sb.append("שעה: ").append(travel.getSelectedTime()).append("\n");
            sb.append("תקופה: ").append(travel.getSelectedEra()).append("\n");
            sb.append("פעילויות: ");
            //הוספת פעילויות היסטוריות
            if (travel.getSelectedActivities() != null && !travel.getSelectedActivities().isEmpty()) {
                for (co.median.android.id_211869409_209052786_ex_2.models.Activity act : travel.getSelectedActivities()) {
                    sb.append(act.getTitle()).append(", ");
                }
                sb.setLength(sb.length() - 2); 
            } else {
                sb.append("אין");
            }
//שפיכה של התוצאות לtextview והוספה של הview
            summaryView.setText(sb.toString());
            summariesContainer.addView(summaryView);
        }
    }
}

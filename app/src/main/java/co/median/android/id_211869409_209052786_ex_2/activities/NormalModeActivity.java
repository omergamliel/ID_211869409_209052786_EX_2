package co.median.android.id_211869409_209052786_ex_2.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.fragments.ActivitiesSelectionFragment;
import co.median.android.id_211869409_209052786_ex_2.fragments.DateTimePickerFragment;
import co.median.android.id_211869409_209052786_ex_2.fragments.EraSelectionFragment;
import co.median.android.id_211869409_209052786_ex_2.fragments.SummaryFragment;
import co.median.android.id_211869409_209052786_ex_2.models.Travel;

public class NormalModeActivity extends AppCompatActivity {

    private Travel currentTravel = new Travel("", "", "", new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_mode);

        if (savedInstanceState == null) {
            replaceFragment(new DateTimePickerFragment(), false);
        }
    }

    public Travel getCurrentTravel() {
        return currentTravel;
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void navigateToActivitiesSelection() {
        replaceFragment(new ActivitiesSelectionFragment(), true);
    }

    public void navigateToEraSelection() {
        replaceFragment(new EraSelectionFragment(), true);
    }

    public void navigateToSummary() {
        replaceFragment(new SummaryFragment(), true);
    }
}

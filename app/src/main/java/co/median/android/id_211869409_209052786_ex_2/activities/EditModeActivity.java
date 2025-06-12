package co.median.android.id_211869409_209052786_ex_2.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.fragments.ActivitiesManageFragment;
import co.median.android.id_211869409_209052786_ex_2.fragments.SummriesManageFragment;


public class EditModeActivity extends AppCompatActivity {

    private Button normalModeButton; 
    private Button editModeButton;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);

        normalModeButton = findViewById(R.id.normalModeButton);
        editModeButton = findViewById(R.id.editModeButton);

        normalModeButton.setOnClickListener(v ->
                replaceFragment(new ActivitiesManageFragment()));

        editModeButton.setOnClickListener(v ->
                replaceFragment(new SummriesManageFragment()));

        if (savedInstanceState == null) {
            replaceFragment(new ActivitiesManageFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}


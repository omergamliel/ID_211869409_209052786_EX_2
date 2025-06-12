// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.activities;

// מסך הפתיחה שמאפשר בחירה בין מצב רגיל למצב עריכה.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.median.android.id_211869409_209052786_ex_2.R;

public class MainActivity extends AppCompatActivity {

    private Button normalModeButton;
    private Button editModeButton;


    @Override
    // אתחול המסך הראשי והגדרת לחצנים למצב עבודה
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        normalModeButton = findViewById(R.id.normalModeButton);
        editModeButton = findViewById(R.id.editModeButton);

        normalModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NormalModeActivity.class);
                startActivity(intent);
            }
        });

        editModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditModeActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
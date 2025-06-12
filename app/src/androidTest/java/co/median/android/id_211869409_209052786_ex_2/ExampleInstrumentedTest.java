package co.median.android.id_211869409_209052786_ex_2;

// בדיקת אינסטרומנטציה לדוגמה שרצה על מכשיר אנדרואיד.

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    // בדיקה שהחבילה של האפליקציה תקינה
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("co.median.android.id_211869409_209052786_ex_2", appContext.getPackageName());
    }
}
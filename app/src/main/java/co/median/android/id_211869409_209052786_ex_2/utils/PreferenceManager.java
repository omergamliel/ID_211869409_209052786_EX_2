package co.median.android.id_211869409_209052786_ex_2.utils;

// מחלקה זו מנהלת את ההעדפות המקומיות של המשתמש ושומרת את בחירותיו השונות.

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import co.median.android.id_211869409_209052786_ex_2.models.Activity;

public class PreferenceManager {

    private static final String PREFS_NAME = "TimeJourneyPrefs";
    private static final String KEY_DATE = "selected_date";
    private static final String KEY_TIME = "selected_time";
    private static final String KEY_ERA_NAME = "selected_era_name";
    private static final String KEY_ACTIVITY_IDS = "selected_activity_ids"; 

    private final SharedPreferences sharedPreferences;

    // בנאי שמאתחל את מחלקת ההעדפות בעזרת הקונטקסט של האפליקציה
    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // שמירת התאריך שנבחר
    public void saveSelectedDate(String date) {
        sharedPreferences.edit().putString(KEY_DATE, date).apply();
    }

    // קבלת התאריך השמור
    public String getSelectedDate() {
        return sharedPreferences.getString(KEY_DATE, null);
    }

    // שמירת השעה שנבחרה
    public void saveSelectedTime(String time) {
        sharedPreferences.edit().putString(KEY_TIME, time).apply();
    }

    // קבלת השעה השמורה
    public String getSelectedTime() {
        return sharedPreferences.getString(KEY_TIME, null);
    }

    
    // שמירת שם התקופה שנבחרה
    public void saveSelectedEraName(String eraName) {
        sharedPreferences.edit().putString(KEY_ERA_NAME, eraName).apply();
    }

    // קבלת שם התקופה השמור
    public String getSelectedEraName() {
        return sharedPreferences.getString(KEY_ERA_NAME, null);
    }

    
    // שמירת מזהי הפעילויות שנבחרו
    public void saveSelectedActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) {
            sharedPreferences.edit().remove(KEY_ACTIVITY_IDS).apply();
            return;
        }
        
        String idsString = activities.stream()
                .map(activity -> String.valueOf(activity.getId()))
                .collect(Collectors.joining(","));
        sharedPreferences.edit().putString(KEY_ACTIVITY_IDS, idsString).apply();
    }

    // קבלת רשימת מזהי הפעילויות השמורות
    public List<String> getSelectedActivityIds() {
        String idsString = sharedPreferences.getString(KEY_ACTIVITY_IDS, null);
        if (idsString == null || idsString.isEmpty()) {
            return new ArrayList<>();
        }
        
        return Arrays.asList(idsString.split(","));
    }

    // שמירת סדר העדיפויות של הפעילויות
    public void savePriorityActivityIds(List<Integer> ids) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder sb = new StringBuilder();
        for (int id : ids) {
            sb.append(id).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); 
        }
        editor.putString("priority_activity_ids", sb.toString());
        editor.apply();
    }

    // קבלת סדר העדיפויות השמור
    public List<Integer> getPriorityActivityIds() {
        String csv = sharedPreferences.getString("priority_activity_ids", "");
        List<Integer> ids = new ArrayList<>();
        if (!csv.isEmpty()) {
            String[] split = csv.split(",");
            for (String s : split) {
                try {
                    ids.add(Integer.parseInt(s));
                } catch (NumberFormatException ignored) {}
            }
        }
        return ids;
    }


    // ניקוי כל ההעדפות מהאחסון
    public void clearPreferences() {
        sharedPreferences.edit().clear().apply();
    }
}
package co.median.android.id_211869409_209052786_ex_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import co.median.android.id_211869409_209052786_ex_2.models.Activity;
import co.median.android.id_211869409_209052786_ex_2.models.Travel;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "time_journey.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database...");

        // טבלת פעילויות היסטוריות
        db.execSQL("CREATE TABLE activities (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT, " +
                "date TEXT" +
                ");");

        // טבלת תקופות זמן
        db.execSQL("CREATE TABLE eras (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "image_name TEXT NOT NULL" +
                ");");

        // טבלת סיכומים
        db.execSQL("CREATE TABLE summaries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "selected_date TEXT NOT NULL, " +
                "selected_time TEXT NOT NULL, " +
                "selected_era TEXT NOT NULL, " +
                "activities TEXT NOT NULL" +
                ");");

        insertInitialActivities(db);
        insertInitialEras(db);
    }

    private void insertInitialActivities(SQLiteDatabase db) {
        insertActivity(db, "The First Olympic Games", "The first recorded Olympic Games held in Ancient Greece.", "0776-07-21");
        insertActivity(db, "Construction of the Colosseum", "The iconic Roman amphitheater is completed in Rome.", "0080-06-01");
        insertActivity(db, "Discovery of Bronze Metallurgy", "Early civilizations begin using bronze, marking the Bronze Age.", "03300-01-01");
        insertActivity(db, "Columbus Reaches the Americas", "Christopher Columbus lands in the New World.", "1492-10-12");
        insertActivity(db, "The First Steam Engine Patent", "Thomas Savery patents the first commercially used steam engine.", "1698-07-02");
    }

    private void insertInitialEras(SQLiteDatabase db) {
        insertEra(db, "Ancient Greece", "ancientgreece");
        insertEra(db, "Roman Empire", "romanempire");
        insertEra(db, "Bronze Age", "bronzeage");
        insertEra(db, "Age of Discovery", "ageofdiscovery");
        insertEra(db, "Industrial Revolution", "industrialrevolution");
    }


    private void insertActivity(SQLiteDatabase db, String title, String description, String date) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", date);
        db.insert("activities", null, values);
    }

    private void insertEra(SQLiteDatabase db, String name, String imageName) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image_name", imageName);
        db.insert("eras", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database...");
        db.execSQL("DROP TABLE IF EXISTS activities");
        db.execSQL("DROP TABLE IF EXISTS summaries");
        db.execSQL("DROP TABLE IF EXISTS eras");
        onCreate(db);
    }

    // פעילויות
    public Cursor getAllActivities() {
        return getReadableDatabase().rawQuery("SELECT * FROM activities", null);
    }

    public long insertActivity(String title, String description, String date) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", date);
        return getWritableDatabase().insert("activities", null, values);
    }

    public void updateActivity(int id, String title, String description, String date) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", date);
        getWritableDatabase().update("activities", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteActivity(int id) {
        getWritableDatabase().delete("activities", "id = ?", new String[]{String.valueOf(id)});
    }

    // תקופות
    public Cursor getAllEras() {
        return getReadableDatabase().rawQuery("SELECT * FROM eras", null);
    }

    // סיכומים
    public long insertTravel(Travel travel) {
        if (travel == null) return -1;

        ContentValues values = new ContentValues();
        values.put("selected_date", travel.getSelectedDate());
        values.put("selected_time", travel.getSelectedTime());
        values.put("selected_era", travel.getSelectedEra());

        // הפוך את רשימת הפעילויות למחרוזת מזהים מופרדת בפסיקים
        StringJoiner joiner = new StringJoiner(", ");
        for (Activity activity : travel.getSelectedActivities()) {
            joiner.add(String.valueOf(activity.getId()));
        }
        values.put("activities", joiner.toString());

        return getWritableDatabase().insert("summaries", null, values);
    }



    public List<Travel> getAllSummaries() {
        List<Travel> summaryList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM summaries"; // השם של הטבלה שלך
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("selected_date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("selected_time"));
                String era = cursor.getString(cursor.getColumnIndexOrThrow("selected_era"));
                String activitiesText = cursor.getString(cursor.getColumnIndexOrThrow("activities")); // לדוג' "1,2,3"
                Travel travel = new Travel(date, time, era, new ArrayList<>());

                summaryList.add(travel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return summaryList;
    }




    public void deleteSummary(long id) {
        getWritableDatabase().delete("summaries", "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllSummaries() {
        getWritableDatabase().execSQL("DELETE FROM summaries");
    }
}

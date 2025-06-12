// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.models;

// מודל של פעילות היסטורית הכוללת מזהה, כותרת, תיאור ותאריך היסטורי.
import java.io.Serializable;

public class Activity implements Serializable {
    private int id;
    private String title;
    private String description;
    private String historicalDate;

    // בנאי המאפשר יצירת פעילות עם מזהה קיים
    public Activity(int id, String title, String description, String historicalDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.historicalDate = historicalDate;
    }

    // בנאי ליצירת פעילות חדשה ללא מזהה קיים
    public Activity(String title, String description, String historicalDate) {
        this(-1, title, description, historicalDate);
    }

    // מחזיר את מזהה הפעילות
    public int getId() { return id; }
    // מעדכן את מזהה הפעילות
    public void setId(int id) { this.id = id; }

    // מחזיר את כותרת הפעילות
    public String getTitle() { return title; }
    // מעדכן את כותרת הפעילות
    public void setTitle(String title) { this.title = title; }

    // מחזיר את תיאור הפעילות
    public String getDescription() { return description; }
    // מעדכן את תיאור הפעילות
    public void setDescription(String description) { this.description = description; }

    // מחזיר את התאריך ההיסטורי של הפעילות
    public String getHistoricalDate() { return historicalDate; }
    // מעדכן את התאריך ההיסטורי של הפעילות
    public void setHistoricalDate(String historicalDate) { this.historicalDate = historicalDate; }
}

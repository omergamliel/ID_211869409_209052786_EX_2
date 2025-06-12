// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.models;

// מודל המתאר מסע בזמן כולל תאריך, שעה, תקופה ופעילויות נבחרות.

import java.util.List;

public class Travel {
    private int id;
    private String selectedDate;
    private String selectedTime;
    private String selectedEra;
    private List<Activity> selectedActivities;

    // בנאי למסע קיים הכולל מזהה
    public Travel(int id, String selectedDate, String selectedTime, String selectedEra, List<Activity> selectedActivities) {
        this.id = id;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedEra = selectedEra;
        this.selectedActivities = selectedActivities;
    }

    // בנאי ליצירת מסע חדש ללא מזהה קיים
    public Travel(String selectedDate, String selectedTime, String selectedEra, List<Activity> selectedActivities) {
        this(-1, selectedDate, selectedTime, selectedEra, selectedActivities);
    }

    // מחזיר את מזהה המסע
    public int getId() { return id; }
    // מעדכן את מזהה המסע
    public void setId(int id) { this.id = id; }

    // מחזיר את התאריך שנבחר
    public String getSelectedDate() { return selectedDate; }
    // מעדכן את התאריך שנבחר
    public void setSelectedDate(String selectedDate) { this.selectedDate = selectedDate; }

    // מחזיר את השעה שנבחרה
    public String getSelectedTime() { return selectedTime; }
    // מעדכן את השעה שנבחרה
    public void setSelectedTime(String selectedTime) { this.selectedTime = selectedTime; }

    // מחזיר את שם התקופה שנבחרה
    public String getSelectedEra() { return selectedEra; }
    // מעדכן את התקופה שנבחרה
    public void setSelectedEra(String selectedEra) { this.selectedEra = selectedEra; }

    // מחזיר את רשימת הפעילויות שנבחרו
    public List<Activity> getSelectedActivities() { return selectedActivities; }
    // מעדכן את רשימת הפעילויות שנבחרו
    public void setSelectedActivities(List<Activity> selectedActivities) { this.selectedActivities = selectedActivities; }
}

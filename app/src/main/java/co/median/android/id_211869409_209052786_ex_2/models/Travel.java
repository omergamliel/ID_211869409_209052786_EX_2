package co.median.android.id_211869409_209052786_ex_2.models;

import java.util.List;

public class Travel {
    private int id;
    private String selectedDate;
    private String selectedTime;
    private String selectedEra;
    private List<Activity> selectedActivities;

    public Travel(int id, String selectedDate, String selectedTime, String selectedEra, List<Activity> selectedActivities) {
        this.id = id;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedEra = selectedEra;
        this.selectedActivities = selectedActivities;
    }

    public Travel(String selectedDate, String selectedTime, String selectedEra, List<Activity> selectedActivities) {
        this(-1, selectedDate, selectedTime, selectedEra, selectedActivities);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSelectedDate() { return selectedDate; }
    public void setSelectedDate(String selectedDate) { this.selectedDate = selectedDate; }

    public String getSelectedTime() { return selectedTime; }
    public void setSelectedTime(String selectedTime) { this.selectedTime = selectedTime; }

    public String getSelectedEra() { return selectedEra; }
    public void setSelectedEra(String selectedEra) { this.selectedEra = selectedEra; }

    public List<Activity> getSelectedActivities() { return selectedActivities; }
    public void setSelectedActivities(List<Activity> selectedActivities) { this.selectedActivities = selectedActivities; }
}

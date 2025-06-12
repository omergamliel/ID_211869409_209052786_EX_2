package co.median.android.id_211869409_209052786_ex_2.models;
import java.io.Serializable;

public class Activity implements Serializable {
    private int id;
    private String title;
    private String description;
    private String historicalDate;

    public Activity(int id, String title, String description, String historicalDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.historicalDate = historicalDate;
    }

    public Activity(String title, String description, String historicalDate) {
        this(-1, title, description, historicalDate);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHistoricalDate() { return historicalDate; }
    public void setHistoricalDate(String historicalDate) { this.historicalDate = historicalDate; }
}

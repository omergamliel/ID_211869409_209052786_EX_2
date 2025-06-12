package co.median.android.id_211869409_209052786_ex_2.models;

// מודל המייצג תקופה היסטורית ושומר את שמה ואת מזהה התמונה שלה.
import java.io.Serializable;

public class Era {
    private String name;
    private int imageResId; 

    // בנאי לקבלת שם התקופה ומזהה התמונה
    public Era(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // מחזיר את שם התקופה
    public String getName() {
        return name;
    }

    // מחזיר את מזהה התמונה של התקופה
    public int getImageResId() {
        return imageResId;
    }
}

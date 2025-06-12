package co.median.android.id_211869409_209052786_ex_2.models;
import java.io.Serializable;

public class Era {
    private String name;
    private int imageResId; // מזהה של התמונה (למשל: R.drawable.renaissance)

    public Era(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}

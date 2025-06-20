// Students Name: Omer Gamliel | ID: 209052786
// Students Name: Batel Gofleyzer | ID: 211869409
// Course Name: 62187 Application Development for Smart Devices

package co.median.android.id_211869409_209052786_ex_2.adapters;

// Adapter המציג את תקופות הזמן ברשימה לבחירת המשתמש.

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import co.median.android.id_211869409_209052786_ex_2.R;
import co.median.android.id_211869409_209052786_ex_2.models.Era;

public class EraAdapter extends RecyclerView.Adapter<EraAdapter.EraViewHolder> {

    public interface OnEraSelectedListener {
        void onEraSelected(Era era);
    }

    private final List<Era> eraList;
    private final OnEraSelectedListener listener;
    private int selectedPosition = -1;

    // בנאי שמקבל את רשימת התקופות ומאזין לבחירה
    public EraAdapter(List<Era> eraList, OnEraSelectedListener listener) {
        this.eraList = eraList;
        this.listener = listener;
    }

    @NonNull
    @Override
    // יצירת ViewHolder של תקופה
    public EraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_era, parent, false);
        return new EraViewHolder(view);
    }

    @Override
    // קישור נתוני התקופה לתצוגה
    public void onBindViewHolder(@NonNull EraViewHolder holder, int position) {
        Era currentEra = eraList.get(position);
        holder.bind(currentEra, position);
    }

    @Override
    public int getItemCount() {
        return eraList.size();
    }

    class EraViewHolder extends RecyclerView.ViewHolder {
        private final ImageView eraImageView;
        private final TextView eraNameTextView;
        private final CardView cardView;

        // בנאי ViewHolder המאתחל את רכיבי הפריט
        public EraViewHolder(@NonNull View itemView) {
            super(itemView);
            eraImageView = itemView.findViewById(R.id.era_imageview);
            eraNameTextView = itemView.findViewById(R.id.era_name_textview);
            cardView = (CardView) itemView;
        }

        //  הצגת תקופה וטיפול בבחירה וסימונה
        void bind(final Era era, final int position) {
            eraNameTextView.setText(era.getName());
            eraImageView.setImageResource(era.getImageResId());

            if (selectedPosition == position) {
                cardView.setCardBackgroundColor(Color.parseColor("#B2DFDB")); 
            } else {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            itemView.setOnClickListener(v -> {
                if (selectedPosition != getAdapterPosition()) {
                    notifyItemChanged(selectedPosition); 
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition); 
                    listener.onEraSelected(era);
                }
            });
        }
    }
}
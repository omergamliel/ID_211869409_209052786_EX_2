package co.median.android.id_211869409_209052786_ex_2.adapters;

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

    public EraAdapter(List<Era> eraList, OnEraSelectedListener listener) {
        this.eraList = eraList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_era, parent, false);
        return new EraViewHolder(view);
    }

    @Override
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

        public EraViewHolder(@NonNull View itemView) {
            super(itemView);
            eraImageView = itemView.findViewById(R.id.era_imageview);
            eraNameTextView = itemView.findViewById(R.id.era_name_textview);
            cardView = (CardView) itemView;
        }

        void bind(final Era era, final int position) {
            eraNameTextView.setText(era.getName());
            eraImageView.setImageResource(era.getImageResId());

            if (selectedPosition == position) {
                cardView.setCardBackgroundColor(Color.parseColor("#B2DFDB")); // Teal Light
            } else {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            itemView.setOnClickListener(v -> {
                if (selectedPosition != getAdapterPosition()) {
                    notifyItemChanged(selectedPosition); // Unselect old
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition); // Select new
                    listener.onEraSelected(era);
                }
            });
        }
    }
}
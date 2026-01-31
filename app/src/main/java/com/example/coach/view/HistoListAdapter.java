package com.example.coach.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.R;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.ViewHolder> {

    private final List<Profil> profils;
    private final IHistoView vue;

    public HistoListAdapter(List<Profil> profils, IHistoView vue) {
        this.profils = profils;
        this.vue = vue;
    }

    @NonNull
    @Override
    public HistoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.layout_liste_histo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoListAdapter.ViewHolder holder, int position) {
        Double img = profils.get(position).getImg();
        holder.txtListIMG.setText(String.format("%.01f", img));

        Date dateMesure = profils.get(position).getDateMesure();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        holder.txtListDate.setText(sdf.format(dateMesure));
    }

    @Override
    public int getItemCount() {
        return profils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView txtListDate;
        public final TextView txtListIMG;
        public final ImageButton btnListSuppr;

        private HistoPresenter presenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtListDate = itemView.findViewById(R.id.txtListDate);
            txtListIMG = itemView.findViewById(R.id.txtListIMG);
            btnListSuppr = itemView.findViewById(R.id.btnListSuppr);

            init();
        }

        private void init() {
            presenter = new HistoPresenter(vue);

            btnListSuppr.setOnClickListener(v -> btnListSuppr_clic());
            txtListDate.setOnClickListener(v -> txtListDateOrImg_clic());
            txtListIMG.setOnClickListener(v -> txtListDateOrImg_clic());
        }

        private void btnListSuppr_clic() {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) return;

            presenter.supprProfil(profils.get(position), new ICallbackApi<Void>() {
                @Override
                public void onSuccess(Void result) {
                    profils.remove(position);
                    notifyItemRemoved(position);
                }

                @Override
                public void onError() {
                    // rien
                }
            });
        }

        private void txtListDateOrImg_clic() {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) return;

            presenter.transfertProfil(profils.get(position));
        }
    }
}

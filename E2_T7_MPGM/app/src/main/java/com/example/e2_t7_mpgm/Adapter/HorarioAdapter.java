package com.example.e2_t7_mpgm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.R;

import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.HorarioViewHolder> {

    private List<HorarioRow> horarios;

    public HorarioAdapter(List<HorarioRow> horarios) {
        this.horarios = horarios;
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout para cada ítem
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horarios, parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorarioViewHolder holder, int position) {
        HorarioRow horario = horarios.get(position);

        // Llenar los TextViews con los datos de la fila
        holder.lunesText.setText(horario.getLunes());
        holder.martesText.setText(horario.getMartes());
        holder.miercolesText.setText(horario.getMiercoles());
        holder.juevesText.setText(horario.getJueves());
        holder.viernesText.setText(horario.getViernes());
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

    public static class HorarioViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los TextViews de la fila
        TextView lunesText, martesText, miercolesText, juevesText, viernesText;

        public HorarioViewHolder(View itemView) {
            super(itemView);
            lunesText = itemView.findViewById(R.id.lunesText);
            martesText = itemView.findViewById(R.id.martesText);
            miercolesText = itemView.findViewById(R.id.miercolesText);
            juevesText = itemView.findViewById(R.id.juevesText);
            viernesText = itemView.findViewById(R.id.viernesText);
        }
    }
}


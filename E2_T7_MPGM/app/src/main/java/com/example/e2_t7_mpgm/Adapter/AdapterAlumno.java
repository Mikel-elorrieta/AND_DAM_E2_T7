package com.example.e2_t7_mpgm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.R;

import java.util.List;

import modelo.Users;

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.UserViewHolder> {

    private List<Users> usersList;

    public AdapterAlumno(List<Users> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users user = usersList.get(position);
        holder.txtUsername.setText(user.getNombre());
        holder.txtEmail.setText(user.getApellidos());
        holder.txtNombre.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtEmail, txtNombre;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtNombre = itemView.findViewById(R.id.txtNombre);
        }
    }
}

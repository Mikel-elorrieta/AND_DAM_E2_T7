package com.example.e2_t7_mpgm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.Adapter.AdapterAlumno;

import java.util.ArrayList;
import java.util.List;

import modelo.Users;
import modelo.Tipos;

public class Alumnos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterAlumno usersAdapter;
    private List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        recyclerView = findViewById(R.id.recyclerViewAlumnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar datos desde un ArrayList de Users
        usersList = new ArrayList<>();
        cargarUsuarios();

        usersAdapter = new AdapterAlumno(usersList);
        recyclerView.setAdapter(usersAdapter);
    }

    private void cargarUsuarios() {
       // usersList.add(new Users(1, new Tipos(1, "Admin"), "usuario1@email.com", "usuario1", "pass1",


    }
}

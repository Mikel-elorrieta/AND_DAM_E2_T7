package com.example.e2_t7_mpgm;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.Adapter.AdapterAlumno;
import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.util.ArrayList;
import java.util.List;

import modelo.Users;

public class Alumnos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterAlumno usersAdapter;
    private List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAlumnos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista y el adaptador
        usersList = new ArrayList<>();
        usersAdapter = new AdapterAlumno(usersList);
        recyclerView.setAdapter(usersAdapter);

        // Cargar los usuarios desde la base de datos o API
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        Konexioa.ask("getAllAlumnos/", new Konexioa.AskCallback() {
            @Override
            public void onSuccess(Object result) {
                runOnUiThread(() -> {
                    if (result instanceof ArrayList<?>) {
                        ArrayList<Users> resultList = (ArrayList<Users>) result;

                        usersList.clear();  // Limpiar la lista para evitar duplicados
                        usersList.addAll(resultList);  // Agregar los nuevos datos
                        usersAdapter.notifyDataSetChanged();  // Notificar al adaptador que los datos cambiaron
                    } else {
                        Toast.makeText(Alumnos.this, "Error Alumnos", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.d("Error Alumnos", e.getMessage());
                    Toast.makeText(Alumnos.this, "Error recuperando Alumnos", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}

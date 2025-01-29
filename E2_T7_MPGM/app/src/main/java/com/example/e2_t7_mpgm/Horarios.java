package com.example.e2_t7_mpgm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.Adapter.HorarioAdapter;
import com.example.e2_t7_mpgm.Adapter.HorarioRow;
import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.util.ArrayList;
import java.util.List;

import modelo.Matriculaciones;
import modelo.Users;


public class Horarios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HorarioAdapter horarioAdapter;
    private List<HorarioRow> horarios;


    // Registrar el ActivityResultLauncher aquí
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        // Configurar el botón de menú (si es necesario)
        findViewById(R.id.menu).setOnClickListener(v -> {
            Intent intent = new Intent(Horarios.this, Profila.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.horariosRecyclerView);

        // Configurar el LayoutManager con orientación vertical o horizontal
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horarios = new ArrayList<>();


            Konexioa.ask("getHorariosByAlumnoId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    runOnUiThread(() -> {
                        if (result instanceof ArrayList<?>) {  // Verificamos que es un ArrayList, pero sin especificar el tipo aún

                            ArrayList<modelo.Horarios> resultList = (ArrayList<modelo.Horarios>) result;
                            for (modelo.Horarios obj : resultList) {
                                    modelo.Horarios h = (modelo.Horarios) obj;  
                                    Log.d("cortes", h.getId().getDia());

                            }
                        } else {
                            Toast.makeText(Horarios.this, "Error Horarios", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(() -> {
                        Log.d("Error Forgot Password", e.getMessage());
                        Toast.makeText(Horarios.this, "Error recuperando user", Toast.LENGTH_SHORT).show();
                    });
                }
            });

        // Crear y establecer el adaptador
        horarioAdapter = new HorarioAdapter(horarios);
        recyclerView.setAdapter(horarioAdapter);




    }
}

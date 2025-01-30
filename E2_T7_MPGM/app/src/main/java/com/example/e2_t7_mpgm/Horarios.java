package com.example.e2_t7_mpgm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        horarios = new ArrayList<>();

        // Crear y establecer el adaptador
        horarioAdapter = new HorarioAdapter(horarios);
        recyclerView.setAdapter(horarioAdapter);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Konexioa.ask("getHorariosByProfeId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    runOnUiThread(() -> {
                        if (result instanceof ArrayList<?>) {  // Verificamos que es un ArrayList, pero sin especificar el tipo aún

                            ArrayList<modelo.Horarios> resultList = (ArrayList<modelo.Horarios>) result;
                            horarios.clear(); // Limpiamos la lista antes de agregar nuevos datos

                            // Crear una matriz vacía de 6 horas x 5 días (inicializar con cadenas vacías)
                            String[][] horarioMatriz = new String[6][5];

                            for (int i = 0; i < 6; i++) {
                                for (int j = 0; j < 5; j++) {
                                    horarioMatriz[i][j] = ""; // Inicializar celdas vacías
                                }
                            }

                            // Llenar la matriz con los datos de horarios
                            for (modelo.Horarios h : resultList) {


                                int horaIndex = Integer.parseInt(h.getId().getHora()) - 1; // Convertir hora (1-6) a índice (0-5)
                                int diaIndex = getDiaIndex(h.getId().getDia()); // Obtener índice del día (0-4)

                                if (horaIndex >= 0 && horaIndex < 6 && diaIndex >= 0 && diaIndex < 5) {
                                    // Guardar asignatura en la celda correspondiente
                                    horarioMatriz[horaIndex][diaIndex] = h.getModulos().getNombre();
                                }
                            }

                            // Convertir la matriz en objetos HorarioRow
                            for (int i = 0; i < 6; i++) {
                                horarios.add(new HorarioRow(
                                        horarioMatriz[i][0], // Lunes
                                        horarioMatriz[i][1], // Martes
                                        horarioMatriz[i][2], // Miércoles
                                        horarioMatriz[i][3], // Jueves
                                        horarioMatriz[i][4]  // Viernes
                                ));
                            }

                            Log.d("aitzol", horarios.toString());

                            // Notificar cambios al adaptador
                            horarioAdapter.notifyDataSetChanged();

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
        }, 500);

    }

    // Función para obtener el índice del día
    private int getDiaIndex(String dia) {
        switch (dia) {
            case "L/A": return 0;
            case "M/A": return 1;
            case "X": return 2;
            case "J/O": return 3;
            case "V/O": return 4;
            default: return -1; // Retornar -1 si el día no es válido
        }
    }



}

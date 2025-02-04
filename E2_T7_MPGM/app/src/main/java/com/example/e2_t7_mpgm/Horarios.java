package com.example.e2_t7_mpgm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.util.ArrayList;
import java.util.List;

import modelo.VistaHorariosUsuarios;


public class Horarios extends AppCompatActivity {

    private RecyclerView recyclerView;




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

        // Configurar el botón de alumnos (si es necesario)
        findViewById(R.id.btnAlumnos).setOnClickListener(v -> {
            Intent intent = new Intent(Horarios.this, Alumnos.class);
            startActivity(intent);
        });


        TableLayout tableLayout = findViewById(R.id.horariosTable);


            new Handler(Looper.getMainLooper()).postDelayed(() -> {



                if(Global.getUser().getTipos().getName().equals("alumno")) {

                Konexioa.ask("getHorariosByAlumnoId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        runOnUiThread(() -> {
                            if (result instanceof ArrayList<?>) {  // Verificamos que es un ArrayList, pero sin especificar el tipo aún

                                ArrayList<VistaHorariosUsuarios> resultList = (ArrayList<VistaHorariosUsuarios>) result;





                                // Crear una matriz vacía de 6 horas x 5 días (inicializar con cadenas vacías)
                                String[][] horarioMatriz = new String[5][5];

                                for (int i = 0; i < 5; i++) {
                                    for (int j = 0; j < 5; j++) {
                                        horarioMatriz[i][j] = ""; // Inicializar celdas vacías
                                    }
                                }

                                Log.d("cortes",resultList.toString());
                                // Llenar la matriz con los datos de horarios
                                for (VistaHorariosUsuarios v : resultList) {

                                    int horaIndex = Integer.parseInt(v.getHora()) - 1; // Convertir hora (1-6) a índice (0-5)
                                    int diaIndex = getDiaIndex(v.getDia()); // Obtener índice del día (0-4)

                                    if (horaIndex >= 0 && horaIndex < 5 && diaIndex >= 0 && diaIndex < 5) {
                                        // Guardar asignatura en la celda correspondiente
                                        horarioMatriz[horaIndex][diaIndex] = v.getModuloNombre();
                                    }
                                }

                                // Agregar las filas a la tabla
                                for (int i = 0; i < 5; i++) {
                                    TableRow row = new TableRow(Horarios.this);

                                    for (int j = 0; j < 5; j++) {
                                        TextView textView = new TextView(Horarios.this);
                                        textView.setText(horarioMatriz[i][j]);
                                        textView.setPadding(8, 8, 8, 8);
                                        textView.setBackgroundResource(R.drawable.table_cell_border);
                                        textView.setLayoutParams(new TableRow.LayoutParams(
                                                TableRow.LayoutParams.WRAP_CONTENT,
                                                TableRow.LayoutParams.WRAP_CONTENT));

                                        row.addView(textView);
                                    }

                                    // Agregar la fila a la tabla
                                    tableLayout.addView(row);


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

        }else {

                    Konexioa.ask("getHorariosByProfeId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            runOnUiThread(() -> {
                                if (result instanceof ArrayList<?>) {  // Verificamos que es un ArrayList, pero sin especificar el tipo aún

                                    ArrayList<modelo.Horarios> resultList = (ArrayList<modelo.Horarios>) result;


                                    // Crear una matriz vacía de 6 horas x 5 días (inicializar con cadenas vacías)
                                    String[][] horarioMatriz = new String[5][5];

                                    for (int i = 0; i < 5; i++) {
                                        for (int j = 0; j < 5; j++) {
                                            horarioMatriz[i][j] = ""; // Inicializar celdas vacías
                                        }
                                    }

                                    // Llenar la matriz con los datos de horarios
                                    for (modelo.Horarios h : resultList) {


                                        int horaIndex = Integer.parseInt(h.getId().getHora()) - 1; // Convertir hora (1-6) a índice (0-5)
                                        int diaIndex = getDiaIndex(h.getId().getDia()); // Obtener índice del día (0-4)

                                        if (horaIndex >= 0 && horaIndex < 5 && diaIndex >= 0 && diaIndex < 5) {
                                            // Guardar asignatura en la celda correspondiente
                                            horarioMatriz[horaIndex][diaIndex] = h.getModulos().getNombre();
                                        }
                                    }

                                    // Agregar las filas a la tabla
                                    for (int i = 0; i < 5; i++) {
                                        TableRow row = new TableRow(Horarios.this);

                                        for (int j = 0; j < 5; j++) {
                                            TextView textView = new TextView(Horarios.this);
                                            textView.setText(horarioMatriz[i][j]);
                                            textView.setPadding(8, 8, 8, 8);
                                            textView.setBackgroundResource(R.drawable.table_cell_border);
                                            textView.setLayoutParams(new TableRow.LayoutParams(
                                                    TableRow.LayoutParams.WRAP_CONTENT,
                                                    TableRow.LayoutParams.WRAP_CONTENT));

                                            row.addView(textView);
                                        }

                                        // Agregar la fila a la tabla
                                        tableLayout.addView(row);


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

                }
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

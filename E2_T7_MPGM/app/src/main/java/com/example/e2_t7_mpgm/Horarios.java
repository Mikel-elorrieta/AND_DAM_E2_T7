package com.example.e2_t7_mpgm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import modelo.Reuniones;
import modelo.Users;
import modelo.VistaHorariosUsuarios;

public class Horarios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner myspinner;
    private ArrayList<Users> usersList;
    private TableLayout tableLayout;
    private ArrayList<Reuniones> reuniones = new ArrayList<>();

    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        myspinner = findViewById(R.id.spinner);
        tableLayout = findViewById(R.id.horariosTable);
        myspinner.setVisibility(View.GONE);

        findViewById(R.id.menu).setOnClickListener(v -> {
            Intent intent = new Intent(Horarios.this, Profila.class);
            startActivity(intent);
        });

        findViewById(R.id.btnAlumnos).setOnClickListener(v -> {
            Intent intent = new Intent(Horarios.this, Alumnos.class);
            startActivity(intent);
        });



        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (Global.getUser().getTipos().getName().equals("alumno")) {
                myspinner.setVisibility(View.VISIBLE);
                getIrakasleak();
                cargarHorariosAlumno();
                findViewById(R.id.btnAlumnos).setEnabled(false);
            } else {
                cargarHorariosProfesor();
            }
        }, 500);
    }

    private void limpiarTabla() {
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }
    }

    private void llenarTabla(String[][] horarioMatriz) {
        limpiarTabla();

        boolean hayDatos = false;
        for (String[] fila : horarioMatriz) {
            TableRow row = new TableRow(Horarios.this);
            for (String celda : fila) {
                TextView textView = new TextView(Horarios.this);
                textView.setText(celda.isEmpty() ? "" : celda);
                textView.setPadding(8, 8, 8, 8);
                textView.setBackgroundResource(R.drawable.table_cell_border);
                row.addView(textView);
                if (!celda.isEmpty()) hayDatos = true;
            }
            tableLayout.addView(row);
        }

        if (!hayDatos) {
            Toast.makeText(Horarios.this, "No hay horarios disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarHorariosAlumno() {
        Konexioa.ask("getHorariosByAlumnoId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
            @Override
            public void onSuccess(Object result) {
                runOnUiThread(() -> {
                    if (result instanceof ArrayList<?>) {
                        ArrayList<VistaHorariosUsuarios> resultList = (ArrayList<VistaHorariosUsuarios>) result;
                        String[][] horarioMatriz = new String[5][5];

                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                horarioMatriz[i][j] = "";
                            }
                        }

                        for (VistaHorariosUsuarios v : resultList) {
                            int horaIndex = Integer.parseInt(v.getHora()) - 1;
                            int diaIndex = getDiaIndex(v.getDia());

                            if (horaIndex >= 0 && horaIndex < 5 && diaIndex >= 0 && diaIndex < 5) {
                                horarioMatriz[horaIndex][diaIndex] = v.getModuloNombre();
                            }
                        }

                        llenarTabla(horarioMatriz);
                    } else {
                        Toast.makeText(Horarios.this, "Error al cargar horarios", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.d("Error Horarios Alumno", e.getMessage());
                    Toast.makeText(Horarios.this, "Error recuperando horarios", Toast.LENGTH_SHORT).show();
                });
            }
        });

        loadBilerak();
    }

    private void cargarHorariosProfesor() {
        getHorarioByIrakasle(Global.getUser().getId());
    }

    private void getHorarioByIrakasle(int id) {
        Konexioa.ask("getHorariosByProfeId/" + id, new Konexioa.AskCallback() {
            @Override
            public void onSuccess(Object result) {
                runOnUiThread(() -> {
                    if (result instanceof ArrayList<?>) {
                        ArrayList<modelo.Horarios> resultList = (ArrayList<modelo.Horarios>) result;
                        String[][] horarioMatriz = new String[5][5];

                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                horarioMatriz[i][j] = "";
                            }
                        }

                        for (modelo.Horarios h : resultList) {
                            int horaIndex = Integer.parseInt(h.getId().getHora()) - 1;
                            int diaIndex = getDiaIndex(h.getId().getDia());

                            if (horaIndex >= 0 && horaIndex < 5 && diaIndex >= 0 && diaIndex < 5) {
                                horarioMatriz[horaIndex][diaIndex] = h.getModulos().getNombre();
                            }
                        }

                        llenarTabla(horarioMatriz);
                    } else {
                        Toast.makeText(Horarios.this, "No hay horarios para este profesor", Toast.LENGTH_SHORT).show();
                    }
                });
                loadBilerak();
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.d("Error Horarios Profesor", e.getMessage());
                    Toast.makeText(Horarios.this, "Error recuperando horarios", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private int getDiaIndex(String dia) {
        switch (dia) {
            case "L/A": return 0;
            case "M/A": return 1;
            case "X": return 2;
            case "J/O": return 3;
            case "V/O": return 4;
            default: return -1;
        }
    }

    private void getIrakasleak() {
        usersList = new ArrayList<>();
        usersList.add(Global.getUser());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Konexioa.ask("getAllTeachers/", new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    runOnUiThread(() -> {
                        if (result instanceof ArrayList<?>) {
                            ArrayList<Users> resultList = (ArrayList<Users>) result;
                            usersList.addAll(resultList);
                            configurarSpinner();
                        } else {
                            Toast.makeText(Horarios.this, "Error al recuperar usuarios", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(() -> {
                        Log.d("Error Usuarios", e.getMessage());
                        Toast.makeText(Horarios.this, "Error recuperando usuarios", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }, 500);
    }

    private boolean isFirstSelection = true; // Variable de control

    private void configurarSpinner() {
        ArrayAdapter<Users> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usersList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(arrayAdapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSelection) {
                    isFirstSelection = false; // Se cambia el estado para futuras selecciones
                    return; // Se sale sin hacer nada
                }

                if(usersList.get(position).getTipos().getName().equals("alumno")) {
                    recreate();
                } else {
                    getHorarioByIrakasle(usersList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void loadBilerak() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            String url = Global.getUser().getTipos().getName().equals("alumno") ?
                    "getBilerakByAlumnoId/" + Global.getUser().getId() :
                    "getBilerakByProfesorId/" + Global.getUser().getId();

            Konexioa.ask(url, new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    runOnUiThread(() -> {
                        if (result instanceof ArrayList<?>) {
                            reuniones = (ArrayList<Reuniones>) result;
                            Log.d("mikel", String.valueOf(reuniones));

                            for (Reuniones r : reuniones) {
                                Timestamp fecha = r.getFecha();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(fecha);

                                int diaSemana = calendar.get(Calendar.DAY_OF_WEEK) - 2; // Lunes=0, ..., Viernes=4
                                int horaIndex = calendar.get(Calendar.HOUR_OF_DAY) - 8; // 8:00=0, 9:00=1, ..., 12:00=4

                                // Verificamos que el día y la hora estén dentro del rango deseado (lunes-viernes, 8:00-12:00)
                                if (diaSemana >= 0 && diaSemana < 5 && horaIndex >= 0 && horaIndex < 5) {
                                    // Obtener la celda correspondiente en la tabla (saltando el encabezado, por eso horaIndex + 1)
                                    TableRow row = (TableRow) tableLayout.getChildAt(horaIndex + 1);
                                    TextView cell = (TextView) row.getChildAt(diaSemana);

                                    // Si la celda ya tiene contenido, pintarla en gris
                                    if (!cell.getText().toString().isEmpty()) {
                                        cell.setBackgroundColor(getResources().getColor(R.color.grey));
                                    } else {
                                        // Colorear según el estado de la reunión
                                        switch (r.getEstado().toLowerCase()) {
                                            case "aceptada":
                                                cell.setBackgroundColor(getResources().getColor(R.color.green));
                                                break;
                                            case "denegada":
                                                cell.setBackgroundColor(getResources().getColor(R.color.red));
                                                break;
                                            case "pendiente":
                                                cell.setBackgroundColor(getResources().getColor(R.color.yellow));
                                                break;
                                        }
                                        // Añadir el estado o algún texto a la celda
                                        cell.setText(r.getEstado());
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(Horarios.this, "Error Reuniones", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(() -> {
                        Log.d("Error Reuniones", e.getMessage());
                        Toast.makeText(Horarios.this, "Error recuperando Reuniones", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }, 800);
    }

}
package com.example.e2_t7_mpgm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import modelo.Matriculaciones;
import modelo.Users;

public class Profila extends AppCompatActivity {

    private ImageView imgArgazkia;
    private Uri argazkiUri;

    // Registrar el ActivityResultLauncher aquí
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Solicitar permisos en tiempo de ejecución para la cámara
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profila);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView lblIzena = findViewById(R.id.lblIzena);
        TextView lblDni = findViewById(R.id.lblDni);
        TextView lblEmail = findViewById(R.id.lblGmail);
        TextView lblTelefono = findViewById(R.id.lblTelefono);
        TextView lblZikloa = findViewById(R.id.lblZikloa);
        TextView lblMaila = findViewById(R.id.lblMaila);
        imgArgazkia = findViewById(R.id.imgAurreikuspena);
        Button btnArgazkiaAtera = findViewById(R.id.btnAteraArgazki);

        // Registrar el ActivityResultLauncher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Mostrar la imagen tomada en el ImageView
                imgArgazkia.setImageURI(argazkiUri);

                // Convertir la imagen a Base64 después de tomarla
                convertirImagenABase64();
            } else {
                Toast.makeText(Profila.this, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show();
            }
        });

        // Al hacer clic en el botón, abrir la cámara
        btnArgazkiaAtera.setOnClickListener(view -> kameraZabaldu());

        //rellenar campos

        lblIzena.setText(Global.getUser().getNombre());
        lblDni.setText(getString(R.string.dni) + " " +Global.getUser().getDni());
        lblEmail.setText(getString(R.string.mail) + " " + Global.getUser().getEmail());
        lblTelefono.setText(getString(R.string.telefono) +  " " + Global.getUser().getTelefono1().toString());

        if(Global.getUser().getTipos().getName().equals("alumno")) {

            Konexioa.ask("getMatriculacionByUserId/" + Global.getUser().getId(), new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    runOnUiThread(() -> {
                        if (result instanceof Matriculaciones) {
                            Matriculaciones ma = (Matriculaciones) result;
                            lblZikloa.setText(getString(R.string.zikloa) + " " +  ma.getCiclos().getNombre());
                            lblMaila.setText( String.valueOf(ma.getId().getCurso()) + ". " + getString(R.string.maila));
                        } else {
                            Toast.makeText(Profila.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(() -> {
                        Log.d("Error get matriculaciones", e.getMessage());
                        Toast.makeText(Profila.this, "Hey", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }

    }

    // Método para abrir la cámara y guardar la imagen
    private void kameraZabaldu() {
        // Crear un archivo para la foto
        File argazkiFitxategia = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "IMG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            argazkiFitxategia = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            Log.e("KameraZabaldu", "Error al crear archivo para la foto", e);
        }

        if (argazkiFitxategia != null) {
            // Obtener la URI del archivo usando FileProvider
            argazkiUri = FileProvider.getUriForFile(this, "com.example.e2_t7_mpgm.fileprovider", argazkiFitxategia);

            // Crear el Intent para abrir la cámara
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, argazkiUri);  // Especificar la URI donde se guardará la foto

            // Lanzar el Intent para abrir la cámara usando el ActivityResultLauncher
            cameraLauncher.launch(intent);
        }


    }

    // Convertir la imagen a Base64
    private void convertirImagenABase64() {
        try {
            // Obtener un InputStream para leer la imagen
            InputStream inputStream = new FileInputStream(new File(argazkiUri.getPath()));
            byte[] imageBytes = new byte[(int) new File(argazkiUri.getPath()).length()];
            inputStream.read(imageBytes);
            inputStream.close();

            // Convertir la imagen a Base64
            String base64String = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                base64String = Base64.getEncoder().encodeToString(imageBytes);
            }

            Konexioa.AskCallback callback = new Konexioa.AskCallback() {
                @Override
                public void onSuccess(Object result) {
                    if (result != null) {
                        System.out.println((boolean) result);
                    }
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Error al realizar la consulta: " + e.getMessage());
                }
            };

            // Llamar al método ask() pasando la clave y el callback
            Konexioa.ask("saveImg/" + base64String, callback);

        } catch (IOException e) {
            Log.e("Profila", "Error al convertir la imagen a Base64", e);
        }
    }

    // Manejar los permisos de la cámara
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El permiso de cámara es necesario", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
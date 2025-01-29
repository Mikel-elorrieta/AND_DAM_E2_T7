package com.example.e2_t7_mpgm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.e2_t7_mpgm.Dao.Konexioa;

import java.util.concurrent.CountDownLatch;

import modelo.Users;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private Button btnForgotPassword;
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView imageView = findViewById(com.example.e2_t7_mpgm.R.id.logoElorrieta);
        Glide.with(this)
                .asGif()
                .load(com.example.e2_t7_mpgm.R.raw.logogif) // Archivo GIF en res/raw
                .into(imageView);

        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPassword = findViewById(R.id.btnForgot);
        edtUsername = findViewById(R.id.editTextUser2);
        edtPassword = findViewById(R.id.editTextPassword);

        btnForgotPassword.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("Pasahitz berreskurapena, Sartu zure Email-a:");

            // Crear un EditText para que el usuario ingrese el correo
            final EditText input = new EditText(Login.this);
            input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            builder.setView(input);

            // Configurar los botones del diálogo
            builder.setPositiveButton("Bidali", (dialog, which) -> {
                String email = input.getText().toString();
                forgotPassword(email);
            });
            builder.setNegativeButton("Itxi", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            login(username, password, new LoginCallback() {
                @Override
                public void onResult(boolean isLoginOk) {
                    if (isLoginOk) {
                        Toast.makeText(Login.this, "Login Exitoso", Toast.LENGTH_SHORT).show();

                        //
                        Konexioa.ask("getUserByName/" + username, new Konexioa.AskCallback() {
                            @Override
                            public void onSuccess(Object result) {
                                runOnUiThread(() -> {
                                    if (result instanceof Users) {
                                        Users user = (Users) result;
                                        Global.setUser(user);
                                    } else {
                                        Toast.makeText(Login.this, "Error user", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                runOnUiThread(() -> {
                                    Log.d("Error Forgot Password", e.getMessage());
                                    Toast.makeText(Login.this, "Error recuperando user", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });

                        //
                        Intent intent = new Intent(Login.this, Horarios.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        });
    }

    public void forgotPassword(String email){
        Konexioa.ask("forgotPassword/" + email, new Konexioa.AskCallback() {
            @Override
            public void onSuccess(Object result) {
                runOnUiThread(() -> {
                    if (result instanceof Boolean) {
                        if ((boolean) result) {
                            Toast.makeText(Login.this, "Pasahitza berreskuratzeko argibideak dituzten mezu bat bidali da eskera.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Ez da aurkitu zure email-a", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Ezin izan da bidali", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Log.d("Error Forgot Password", e.getMessage());
                    Toast.makeText(Login.this, "No se ha podido enviar el correo", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    public void login(String username, String password, final LoginCallback callback) {
        // Hacer la consulta asincrónica
        Konexioa.ask("isLoginOk/" + username + "/" + password, new Konexioa.AskCallback() {
            @Override
            public void onSuccess(Object result) {
                // Asegúrate de que la llamada al callback se realice en el hilo de la interfaz de usuario
                runOnUiThread(() -> {
                    if (result instanceof Boolean) {
                        callback.onResult((boolean) result);  // Llamar al callback con el resultado
                    } else {
                        callback.onResult(false);  // Si el resultado no es válido, devolver false
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                // Asegúrate de que la llamada al callback se realice en el hilo de la interfaz de usuario
                runOnUiThread(() -> {
                    Log.d("Error Login", e.getMessage());
                    callback.onResult(false);  // En caso de error, devolver false
                });
            }
        });
    }

    // Interfaz para el callback
    public interface LoginCallback {
        void onResult(boolean isLoginOk);
    }
}

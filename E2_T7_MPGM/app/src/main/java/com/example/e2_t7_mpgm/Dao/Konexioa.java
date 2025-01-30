package com.example.e2_t7_mpgm.Dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Konexioa {

    private static final String SERVER_ADDRESS = "10.5.104.49";
    private static final int SERVER_PORT = 20000;

    private static Socket server;
    private static ObjectInputStream ois;
    private static DataOutputStream dos;

    public static void connect() {
        Thread thread = new Thread(() -> {
            try {
                server = new Socket(SERVER_ADDRESS, SERVER_PORT);
                System.out.println("Konexioa lortu da");
                System.out.println(server);
            } catch (UnknownHostException e) {
                System.out.println("Host-a ez da aurkitu");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Portua ez da aurkitu");
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join(); // Espera a que se complete la conexión
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void ask(String key, AskCallback callback) {
        Thread thread = new Thread(() -> {
            Object obj = null;
            try {
                connect();

                dos = new DataOutputStream(server.getOutputStream());
                dos.writeUTF(key);

                ois = new ObjectInputStream(server.getInputStream());
                obj = ois.readObject();

                if (callback != null) {
                    callback.onSuccess(obj); // Devuelve el objeto al callback
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onError(e); // Devuelve el error al callback
                }
            } finally {
                closeResources();
            }
        });
        thread.start();
    }

    private static void closeResources() {
        try {
            if (ois != null) ois.close();
            if (dos != null) dos.close();
            if (server != null && !server.isClosed()) server.close();
            System.out.println("Recursos cerrados correctamente");
        } catch (IOException e) {
            System.out.println("Error al cerrar recursos");
            e.printStackTrace();
        }
    }

    public interface AskCallback {
        void onSuccess(Object result);

        void onError(Exception e);
    }
}

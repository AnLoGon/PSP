package ud3.exercises.reminder.server;

import ud3.exercises.reminder.models.Reminder;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

public class ReminderServer {
    // Mapa que asocia usuario a su lista de recordatorios.
    // Se utiliza ConcurrentHashMap para manejar acceso concurrente.
    public static ConcurrentHashMap<String, List<Reminder>> remindersDB = new ConcurrentHashMap<>();

    // Variable para generar IDs únicos para recordatorios.
    private static int reminderIdCounter = 1;

    // Método sincronizado para generar ID único.
    public static synchronized int getNextReminderId() {
        return reminderIdCounter++;
    }

    public static void main(String[] args) {
        int port = 5000; // Puerto de escucha

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port);

            // Escuchar conexiones entrantes
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());
                ReminderServerHandler handler = new ReminderServerHandler(socket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
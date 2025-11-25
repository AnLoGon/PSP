package ud3.exercises.reminder.server;

import ud3.exercises.reminder.models.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ReminderServerHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ReminderServerHandler(Socket socket) {
        this.socket = socket;
        try {
            // Es importante crear primero el ObjectOutputStream
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error al crear streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            ReminderMessage message;
            while ((message = (ReminderMessage) ois.readObject()) != null) {
                String username = message.getUsername();
                switch (message.getType()) {
                    case ADD:
                        addReminder(username, message.getReminder());
                        break;
                    case LIST:
                        listReminders(username, message.getCount());
                        break;
                    case DELETE:
                        deleteReminder(username, message.getReminder());
                        break;
                    case EXIT:
                        System.out.println("Cliente " + username + " se ha desconectado.");
                        socket.close();
                        return;
                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            try {
                if(socket != null && !socket.isClosed()){
                    socket.close();
                }
            } catch (IOException e) {
                // Ignorar
            }
        }
    }

    private void addReminder(String username, Reminder reminder) throws IOException {
        // Asignar un ID único al recordatorio
        int id = ReminderServer.getNextReminderId();
        Reminder newReminder = new Reminder(id, reminder.getDescription(), reminder.getDueDate());

        // Obtener o crear la lista para el usuario
        ReminderServer.remindersDB.putIfAbsent(username, Collections.synchronizedList(new ArrayList<>()));
        ReminderServer.remindersDB.get(username).add(newReminder);
        System.out.println("Recordatorio añadido para el usuario " + username + ": " + newReminder);

        // Enviar respuesta de confirmación
        ReminderMessage response = new ReminderMessage(ReminderMessageType.RESPONSE, username);
        oos.writeObject(response);
    }

    private void listReminders(String username, int count) throws IOException {
        List<Reminder> list = ReminderServer.remindersDB.get(username);
        List<Reminder> result = new ArrayList<>();
        if (list != null) {
            // Ordenar por fecha de vencimiento (ascendente)
            list.sort(Comparator.comparing(Reminder::getDueDate));
            for (int i = 0; i < Math.min(count, list.size()); i++) {
                result.add(list.get(i));
            }
        }
        // Enviar la lista en la respuesta
        ReminderMessage response = new ReminderMessage(ReminderMessageType.RESPONSE, username, result, true);
        oos.writeObject(response);
    }

    private void deleteReminder(String username, Reminder reminder) throws IOException {
        List<Reminder> list = ReminderServer.remindersDB.get(username);
        boolean removed = false;
        if (list != null) {
            // Buscar por id
            removed = list.removeIf(r -> r.getId() == reminder.getId());
        }
        if (removed) {
            System.out.println("Recordatorio con id " + reminder.getId() + " eliminado para el usuario " + username);
        } else {
            System.out.println("No se encontró el recordatorio con id " + reminder.getId() + " para el usuario " + username);
        }
        // Enviar respuesta de confirmación
        ReminderMessage response = new ReminderMessage(ReminderMessageType.RESPONSE, username);
        oos.writeObject(response);
    }
}
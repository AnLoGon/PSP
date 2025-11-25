package ud3.exercises.reminder.client;

import ud3.exercises.reminder.models.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ReminderClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();

            boolean exit = false;
            while (!exit) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Añadir un recordatorio");
                System.out.println("2. Listar recordatorios");
                System.out.println("3. Eliminar un recordatorio");
                System.out.println("4. Salir");
                System.out.print("Opción: ");
                String option = scanner.nextLine();

                switch(option) {
                    case "1":
                        // Añadir recordatorio
                        System.out.print("Ingrese la descripción: ");
                        String description = scanner.nextLine();
                        System.out.print("Ingrese la fecha y hora de vencimiento (yyyy-MM-dd HH:mm): ");
                        String dateStr = scanner.nextLine();
                        LocalDateTime dueDate = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                        Reminder reminder = new Reminder(0, description, dueDate);
                        ReminderMessage addMsg = new ReminderMessage(ReminderMessageType.ADD, username, reminder);
                        oos.writeObject(addMsg);
                        // Recibir respuesta
                        ois.readObject();
                        System.out.println("Recordatorio añadido.");
                        break;
                    case "2":
                        // Listar recordatorios
                        System.out.print("Ingrese el número de recordatorios a mostrar: ");
                        int count = Integer.parseInt(scanner.nextLine());
                        ReminderMessage listMsg = new ReminderMessage(ReminderMessageType.LIST, username, count);
                        oos.writeObject(listMsg);
                        // Recibir respuesta
                        ReminderMessage response = (ReminderMessage) ois.readObject();
                        List<Reminder> reminders = response.getReminders();
                        if (reminders != null && !reminders.isEmpty()) {
                            System.out.println("Recordatorios:");
                            for (Reminder r : reminders) {
                                System.out.println(r);
                            }
                        } else {
                            System.out.println("No tiene recordatorios o la cantidad solicitada es 0.");
                        }
                        break;
                    case "3":
                        // Eliminar recordatorio
                        System.out.print("Ingrese el id del recordatorio a eliminar: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        Reminder remToDelete = new Reminder(id, "", LocalDateTime.now());
                        ReminderMessage delMsg = new ReminderMessage(ReminderMessageType.DELETE, username, remToDelete);
                        oos.writeObject(delMsg);
                        // Recibir respuesta
                        ois.readObject();
                        System.out.println("Petición de eliminación enviada.");
                        break;
                    case "4":
                        // Salir
                        ReminderMessage exitMsg = new ReminderMessage(ReminderMessageType.EXIT, username);
                        oos.writeObject(exitMsg);
                        exit = true;
                        System.out.println("Desconectando...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
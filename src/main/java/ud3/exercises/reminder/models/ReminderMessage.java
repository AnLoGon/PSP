package ud3.exercises.reminder.models;

import java.io.Serializable;
import java.util.List;

public class ReminderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private ReminderMessageType type;
    private String username;
    private Reminder reminder;         // Para ADD y DELETE
    private int count;                 // Número de recordatorios a listar
    private List<Reminder> reminders;  // Para enviar la respuesta de LIST

    public ReminderMessage(ReminderMessageType type, String username) {
        this.type = type;
        this.username = username;
    }

    // Constructor para operaciones con Reminder
    public ReminderMessage(ReminderMessageType type, String username, Reminder reminder) {
        this(type, username);
        this.reminder = reminder;
    }

    // Constructor para LIST con count
    public ReminderMessage(ReminderMessageType type, String username, int count) {
        this(type, username);
        this.count = count;
    }

    // Constructor para respuesta con lista de recordatorios
    public ReminderMessage(ReminderMessageType type, String username, List<Reminder> reminders, boolean dummy) {
        // El parámetro dummy se usa solo para diferenciar la firma
        this(type, username);
        this.reminders = reminders;
    }

    public ReminderMessageType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public int getCount() {
        return count;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
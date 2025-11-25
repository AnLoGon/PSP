package ud3.exercises.reminder.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reminder implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private LocalDateTime dueDate;

    public Reminder(int id, String description, LocalDateTime dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Reminder [id=" + id + ", description=" + description + ", dueDate=" + dueDate.format(formatter) + "]";
    }
}
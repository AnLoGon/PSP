package ud3.exercises.reminder.models;

import java.io.Serializable;

public enum ReminderMessageType implements Serializable {
    ADD,       // Añadir recordatorio
    LIST,      // Listar recordatorios
    DELETE,    // Eliminar recordatorio
    RESPONSE,  // Respuesta del servidor
    EXIT       // Cerrar conexión
}
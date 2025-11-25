package ud3.practices.lyrics.client;

import java.io.Serializable;

//Contiene el estado (SUCCESS o ERROR) y los datos devueltos por el servidor.
public class Response implements Serializable {
    private final String status;
    private final String message;

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
}
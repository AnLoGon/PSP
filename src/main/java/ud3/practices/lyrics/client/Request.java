package ud3.practices.lyrics.client;

import java.io.Serializable;

//Representa los mensajes enviados por el cliente (NUM_LINES o GET i).
public class Request implements Serializable {
    private final String type;
    private final int index;

    public Request(String type, int index) {
        this.type = type;
        this.index = index;
    }

    public String getType() { return type; }
    public int getIndex() { return index; }
}
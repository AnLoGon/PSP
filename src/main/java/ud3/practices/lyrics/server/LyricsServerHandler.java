package ud3.practices.lyrics.server;

import ud3.practices.lyrics.client.Request;
import ud3.practices.lyrics.client.Response;
import java.io.*;
import java.net.*;
import java.util.List;

public class LyricsServerHandler extends Thread {
    private final Socket socket;
    private final List<String> lyrics;

    public LyricsServerHandler(Socket socket, List<String> lyrics) {
        this.socket = socket;
        this.lyrics = lyrics;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            while (true) {
                Request request;
                try {
                    request = (Request) in.readObject();
                } catch (EOFException e) {
                    break;
                }
                if (request.getType().equals("NUM_LINES")) {
                    out.writeObject(new Response("SUCCESS", String.valueOf(lyrics.size())));
                } else if (request.getType().equals("GET")) {
                    int index = request.getIndex();
                    if (index >= 0 && index < lyrics.size()) {
                        out.writeObject(new Response("SUCCESS", lyrics.get(index)));
                    } else {
                        out.writeObject(new Response("ERROR", "La línia solicitada no existeix."));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en la connexió con el client: " + e.getMessage());
        }
    }
}
package ud3.practices.lyrics.client;

import java.io.*;
import java.net.*;

public class Loader extends Thread {
    private final LyricsPlayer lyricsPlayer;
    private static final String HOST = "localhost";
    private static final int PORT = 1234;
    private String filename;

    public Loader(LyricsPlayer lyricsPlayer) {
        this.lyricsPlayer = lyricsPlayer;
    }

    public Loader(LyricsPlayer lyricsPlayer, String filename) {
        this.lyricsPlayer = lyricsPlayer;
        this.filename = filename;
    }

    public void run() {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(new Request("NUM_LINES", 0));
            Response response = (Response) in.readObject();
            int numLines = Integer.parseInt(response.getMessage());

            for (int i = 0; i < numLines; i++) {
                out.writeObject(new Request("GET", i));
                response = (Response) in.readObject();
                if (response.getStatus().equals("SUCCESS")) {
                    lyricsPlayer.addLine(response.getMessage());
                    System.err.printf(" (Línia %d rebuda)\n", i);
                }
            }
            lyricsPlayer.setEnd(true);
        } catch (Exception e) {
            System.err.println("Error en la connexió amb el servidor: " + e.getMessage());
        }
    }
}
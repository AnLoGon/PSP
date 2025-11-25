package ud3.practices.lyrics.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class LyricsServer {
    private static final int PORT = 1234;
    private static final String FILE_PATH = "files/ud2/lyrics.txt";
    private final List<String> lyrics;

    public LyricsServer() throws IOException {
        lyrics = new ArrayList<>();
        loadLyrics();
    }

    private void loadLyrics() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                lyrics.add(line);
            }
        }
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor en execució en el port " + PORT);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new LyricsServerHandler(clientSocket, lyrics).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new LyricsServer().start();
    }
}
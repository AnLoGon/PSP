package ud3.exercises.time.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        String command = "DATETIME";
        String timezone = "Europe/Madrid";
        String request = command + " " + timezone;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            String response = in.readLine();
            System.out.println("Response: " + response);

        } catch (UnknownHostException e) {
            System.err.println("Host unknown: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
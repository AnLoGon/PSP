package ud3.exercises.time.server;

import ud3.exercises.time.models.TimeMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServerHandler implements Runnable {
    private Socket clientSocket;

    public TimeServerHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request == null) return;

            String[] parsed = TimeMessage.parse(request);
            String command = parsed[0];
            String timezoneStr = parsed[1];

            if (!isValidCommand(command)) {
                out.println("ERROR: Invalid command");
                return;
            }

            ZoneId zone;
            try {
                zone = ZoneId.of(timezoneStr);
            } catch (DateTimeException e) {
                out.println("ERROR: Invalid timezone");
                return;
            }

            ZonedDateTime zdt = ZonedDateTime.now(zone);
            String response = formatResponse(command, zdt);
            out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidCommand(String command) {
        return command.equals("TIME") || command.equals("DATE") || command.equals("DATETIME");
    }

    private String formatResponse(String command, ZonedDateTime zdt) {
        switch (command) {
            case "TIME":
                return zdt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            case "DATE":
                return zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            case "DATETIME":
                return zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            default:
                return "ERROR";
        }
    }
}
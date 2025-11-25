package ud3.examples.multiclient.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class MulticlientClient {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            int port = 1234;
            System.out.println("Creando el Socket cliente.");
            Socket socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Se puede utilizar la opción autoflush
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Identificamos el cliente en el servidor.
            // Lo primero que hace el servidor es esperar el nombre
            System.out.print("Introduce tu nombre: ");
            String nom = scanner.nextLine();
            out.println(nom);

            // El client puede enviar mensajes hasta que escriba END
            String line;
            System.out.print("Texto: ");
            while(!(line = scanner.nextLine()).equals("END")){
                out.println(line);
                System.out.print("Texto: ");
            }
            // Cerramos la conexión al acabar
            socket.close();
        } catch (ConnectException e) {
            System.err.println("¡Conexión rechazada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
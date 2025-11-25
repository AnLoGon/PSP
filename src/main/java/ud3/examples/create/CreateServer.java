package ud3.examples.create;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateServer {
    public static void main(String[] args) {
        try {
            int port = 1234;
            System.out.println("Creando el Socket servidor en el puerto: " + port);
            ServerSocket server = new ServerSocket( port);

            System.out.println("Esperando conexiones...");
            Socket connexio = server.accept();
            System.out.println("¡Conectado con el cliente!");

            BufferedReader in = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
            PrintWriter out = new PrintWriter(connexio.getOutputStream());

            System.out.println("Esperando mensaje del cliente...");
            String missatge = in.readLine();
            System.out.println("Se ha recibido el mensaje:");
            System.out.println(missatge);

            System.out.println("Se ha enviado el mensaje: \"¡Recibido!\"");
            out.println("¡Recibido!");
            out.flush();

            System.out.println("Cerrando el servidor...");
            connexio.close();
            server.close();
            System.out.println("Cerrado.");
        } catch (ConnectException e) {
            System.err.println("¡Conexión rechazada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
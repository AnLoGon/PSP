package ud3.examples.create;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class CreateClient {
    public static void main(String[] args) {
        try {
            System.out.println("Creando el Socket cliente.");
            Socket socket = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Se puede utilizar la opción autoflush
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String missatge = "Este mensaje ha sido enviado desde el cliente.";
            out.println(missatge);
            out.flush();
            System.out.println("Se ha enviado el mensaje.");

            System.out.println("Esperando respuesta...");
            String resposta = in.readLine();

            System.out.println("Respuesta del servidor:");
            System.out.println(resposta);

            System.out.println("Cerrando el socket...");
            socket.close();
            System.out.println("Cerrado");
        } catch (ConnectException e) {
            System.err.println("¡Conexión rechazada!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
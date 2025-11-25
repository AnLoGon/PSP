package ud3.examples.multiclient.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase que gestiona la comunicación del servidor
 * con un único cliente en un hilo de ejecución independente.
 */
public class MulticlientServerHandler extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private String nom;

    public MulticlientServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        nom = null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void close() throws IOException {
        socket.close();
    }
    /**
     * Hilo de ejecución independente para cada cliente.
     * <p>
     * Antes de nada, el cliente se identifica con un nombre.
     * Después, el servidor muestra los mensajes que cada cliente ha enviado.
     */
    @Override
    public void run() {
        try {
            setNom(in.readLine());
            System.out.printf("%s se ha identificado.\n", getNom());

            // Cuando un cliente se desconecta, la operación readLine() devuelve null
            String message;
            while((message = in.readLine()) != null){
                System.out.printf("%s: %s\n", getNom(), message);
            }
            System.out.printf("%s se ah desconectado.\n", getNom());
            close();
        } catch (IOException e) {
            System.err.println("Error mientras se identificaba el cliente.");
            System.err.println(e.getMessage());
        }
    }
}
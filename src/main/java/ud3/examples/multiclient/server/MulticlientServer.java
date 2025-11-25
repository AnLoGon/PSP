package ud3.examples.multiclient.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MulticlientServer extends Thread {
    private final ServerSocket server;
    private final List<MulticlientServerHandler> clients;
    private boolean running;

    /**
     * Crea un servidor ServerSocket a partir del puerto.
     *
     * @param port Puerto donde escuchará el servidor
     * @throws IOException Excepciones del constructor ServerSocket
     */
    public MulticlientServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        running = true;
    }

    /**
     * Para la ejecución del servidor y todas las gestiones de los clientes
     */
    public void close(){
        running = false;
        for (MulticlientServerHandler client : clients) {
            try {
                client.close();
            } catch (IOException ignored) {
            }
            client.interrupt();
        }
        this.interrupt();
    }

    /**
     * Hilo de ejecución del servidor.
     * <p>
     * El servidor escucha el puerto y espera nuevas conexiones.
     * Cuando un nuevo cliente se conecta, se crea un objeto ServerHandler,
     * que gestionará la comunicación con este cliente en un hilo distinto.
     * <p>
     * De esta manera, el servidor puede continuar escuchando y esperando
     * nuevas conexiones mientras cada hilo gestiona la comunicación
     * con cada cliente.
     */
    @Override
    public void run() {
        while (running){
            try {
                Socket client = server.accept();
                System.out.println("Nuevo cliente aceptado.");
                MulticlientServerHandler handler = new MulticlientServerHandler(client);
                clients.add(handler);
                handler.start();
            } catch (IOException e) {
                System.err.println("Error mientras se aceptaba una nueva conexión.");
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MulticlientServer server = null;
        try {
            server = new MulticlientServer(1234);
            server.start();
            server.join();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex){
            System.out.println("Cerrando el servidor de manera segura...");
            server.close();
        }
    }
}
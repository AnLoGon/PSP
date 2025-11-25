package ud3.exercises.suma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class SumaServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);

            System.out.println("Esperant connexió...");
            Socket connexio = server.accept();
            System.out.println("Connectat amb el client!");

            BufferedReader in = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
            PrintWriter out = new PrintWriter(connexio.getOutputStream());

            System.out.println("Esperant numeros des del client...");
            String numero1 = in.readLine();
            String numero2 = in.readLine();
            System.out.println("S'ha rebut els numeros: " + numero1 + " + " + numero2);

            out.println(Integer.parseInt(numero1) + Integer.parseInt(numero2));
            out.flush();

            connexio.close();
            server.close();
            System.out.println("Tancat el servidor.");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
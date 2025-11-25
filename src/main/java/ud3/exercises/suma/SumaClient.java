package ud3.exercises.suma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class SumaClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Socket socket = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            System.out.println("Escriu el primer número:");
            String numero1 = sc.nextLine();
            System.out.println("Escriu el segon número:");
            String numero2 = sc.nextLine();

            out.println(Integer.parseInt(numero1));
            out.flush();
            out.println(Integer.parseInt(numero2));
            out.flush();

            System.out.println("Esperant resultat de la suma...");
            String resposta = in.readLine();

            System.out.println("La suma es: " + resposta);

            socket.close();
            System.out.println("Tancada la connexió.");
        } catch (ConnectException e) {
            System.err.println("Connection refused!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
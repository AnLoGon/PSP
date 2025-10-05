package ud1.examples;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Locale;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RunProcessInput {
    public static void main (String[] args) {
        String[] program = {"wsl.exe", "tr", "aeiou", "AEIOU"};
        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            Process process = pb.start();

            // Objeto para poder leer la entrada estándar del programa padre
            Scanner in = new Scanner(System.in).useLocale(Locale.US);

            // Objeto para poder escribir en la entrada estándar del proceso hijo
            PrintWriter stdin = new PrintWriter(process.getOutputStream());

            System.out.println("Stdin:");
            String line;
            while(!(line = in.nextLine()).isEmpty())
                stdin.println(line);

            // Cuando acabemos de introducir datos, hay que cerrar el stream
            stdin.flush();
            stdin.close();

            // Objetos para poder leer la salida estándar y el error
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            int codiRetorn = process.waitFor();
            System.out.println("La ejecución de " + Arrays.toString(program) + " ha acabado con el código: " + codiRetorn);

            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null)
                System.out.printf("    %s\n", line);

            System.out.println("Stderr:");
            while ((line = stderr.readLine()) != null)
                System.out.printf("    %s\n", line);

        } catch (IOException ex) {
            System.err.println("Excepción de E/S.");
            System.err.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El proceso hijo ha finalizado de manera incorrecta.");
            System.exit(-1);
        }
    }
}
package ud1.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PracticaProcesos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce un comando y sus argumentos, separados por espacios (ej: ls -la):");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            System.err.println("No se ha introducido ningún comando.");
            return;
        }
        // Dividimos el comando y sus argumentos
        String[] inputTrim = input.trim().split("\\s+");

        // Se construye el comando completo mediante una lista
        List<String> program = new ArrayList<>();
        program.add("wsl");
        program.addAll(Arrays.asList(inputTrim));

        System.out.println("Ejecutando comando: wsl " + input);

        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            // Se inicia el proceso hijo
            Process process = pb.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            int exitCode = process.waitFor();

            // Lectura de la salida estándar y de error
            String line;
            while ((line = stdout.readLine()) != null)
                System.out.printf("    %s\n", line);

            while ((line = stderr.readLine()) != null)
                System.err.printf("Error:    %s\n", line);

            // Código de salida del proceso hijo
            System.out.println("Proceso hijo finalizado con el código de salida: " + exitCode);

            // Cierre de los recursos
            stdout.close();
            stderr.close();
            sc.close();

        // Manejo de excepciones
        } catch (IOException ex) {
            System.err.println("Error de I/O: El comando no se pudo ejecutar.");
            System.err.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("Error: El proceso fue interrumpido.");
            System.exit(-1);
        }
    }
}